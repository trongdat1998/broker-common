/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.interceptor
 *@Date 2018/6/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.interceptor;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import io.bhex.base.grpc.client.channel.IGrpcClientPool;
import io.bhex.broker.common.entity.BHexAppHeaders;
import io.bhex.broker.common.entity.Header;
import io.bhex.broker.common.entity.RequestPlatform;
import io.bhex.broker.common.util.*;
import io.bhex.broker.core.domain.BrokerCoreConstants;
import io.bhex.broker.grpc.broker.Broker;
import io.bhex.broker.grpc.broker.BrokerServiceGrpc;
import io.bhex.broker.grpc.broker.QueryBrokerRequest;
import io.bhex.broker.grpc.broker.QueryBrokerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Slf4j
public class HeaderInterceptor implements HandlerInterceptor {

    private static final String ORG_ID_MDC_KEY = "orgId";

    private static final String DEBUG_PRINT_REQUEST = "printReqDebugInfo";

    private static ImmutableMap<String, Long> BROKER_DOMAIN_ID_MAP = ImmutableMap.of();

    private final IGrpcClientPool pool;

    private final String brokerServerChannelName;

    public HeaderInterceptor(IGrpcClientPool pool, String brokerServerChannelName) {
        this.pool = pool;
        this.brokerServerChannelName = brokerServerChannelName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Boolean.parseBoolean(request.getParameter(DEBUG_PRINT_REQUEST))) {
            RequestUtil.printRequest(request, "");
        }
        Long orgId = null;
        String firstLevelDomain = null;
        for (String domain : BROKER_DOMAIN_ID_MAP.keySet()) {
            if (request.getServerName().endsWith(domain)) {
                firstLevelDomain = domain;
                orgId = BROKER_DOMAIN_ID_MAP.get(domain);
                request.setAttribute(BrokerCoreConstants.ORG_ID_ATTR, orgId);
            }
        }
        if (orgId == null) {
            log.warn("request host: {}, url: {}, cannot find referenced broker", request.getServerName(), request.getRequestURI());
            return false;
        }
        MDC.put(ORG_ID_MDC_KEY, String.valueOf(orgId));
        Header.Builder headerBuilder = HeaderUtil.buildFromHttp(request).toBuilder();
        headerBuilder.orgId(orgId);
        headerBuilder.domain(firstLevelDomain);
        headerBuilder.requestId(CryptoUtil.getRandomCode(12));

        String requestUri = request.getRequestURI();
        headerBuilder.serverUri(requestUri);

        String app = CookieUtil.getValue(request, BHexAppHeaders.APP);
        headerBuilder.platform(RequestPlatform.PC);
        if (((requestUri.startsWith("/api") || requestUri.startsWith("/s_api")) && !Strings.isNullOrEmpty(app))
                || requestUri.startsWith("/mapi") || requestUri.startsWith("/ms_api")) {
            headerBuilder.platform(RequestPlatform.MOBILE);
            headerBuilder.appBaseHeader(HeaderUtil.buildAppRequestHeaderFromRequest(request));
        }
        Header header = headerBuilder.build();
        request.setAttribute(BrokerCoreConstants.PLATFORM_REQUEST_ATTR, header.getPlatform());
        request.setAttribute(BrokerCoreConstants.HEADER_REQUEST_ATTR, header);
        return true;
    }

    @EventListener(value = {ContextRefreshedEvent.class})
    @Scheduled(cron = "0 0/5 * * * ?")
    private void refreshBrokerInfo() {
        BrokerServiceGrpc.BrokerServiceBlockingStub stub = BrokerServiceGrpc.newBlockingStub(pool.borrowChannel(brokerServerChannelName));
        QueryBrokerResponse response = stub.queryBrokers(QueryBrokerRequest.getDefaultInstance());
        List<Broker> brokerList = response.getBrokersList();

        Map<String, Long> tmpBrokerMap = Maps.newHashMap();
        if (brokerList.size() > 0) {
            for (Broker broker : brokerList) {
                if (broker.getStatus() == 1) {
                    for (String domain : broker.getApiDomainsList()) {
                        tmpBrokerMap.put(domain, broker.getOrgId());
                        String masterDomain = domain;
                        if (masterDomain.startsWith(".")) {
                            masterDomain = masterDomain.substring(1);
                        }
                        masterDomain = broker.getOrgId() + "-" + masterDomain.replaceAll("\\.", "-");
                        if (!CollectionUtils.isEmpty(broker.getBackupApiDomainsList())) {
                            for (String backupDomain : broker.getBackupApiDomainsList()) {
                                tmpBrokerMap.put(masterDomain + "." + backupDomain, broker.getOrgId());
                            }
                        }
                        if (!CollectionUtils.isEmpty(broker.getHuaweiCloudDomainsList())) {
                            for (String huaweiCloudDomain : broker.getHuaweiCloudDomainsList()) {
                                tmpBrokerMap.put(masterDomain + "." + huaweiCloudDomain, broker.getOrgId());
                            }
                        }
                    }
                }
            }
            BROKER_DOMAIN_ID_MAP = ImmutableMap.copyOf(tmpBrokerMap);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(ORG_ID_MDC_KEY);
    }
}