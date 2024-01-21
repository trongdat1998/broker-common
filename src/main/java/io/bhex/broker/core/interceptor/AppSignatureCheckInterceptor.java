/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.interceptor
 *@Date 2018/8/12
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.interceptor;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import io.bhex.base.grpc.client.channel.IGrpcClientPool;
import io.bhex.broker.common.entity.RequestPlatform;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.core.domain.BrokerCoreConstants;
import io.bhex.broker.grpc.broker.Broker;
import io.bhex.broker.grpc.broker.BrokerServiceGrpc;
import io.bhex.broker.grpc.broker.QueryBrokerRequest;
import io.bhex.broker.grpc.broker.QueryBrokerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AppSignatureCheckInterceptor implements HandlerInterceptor {

    private static final String SIGN_PARAM_NAME = "sig";

    private static final String DEFAULT_MD5_SALT = "bhex.com";

    private static ImmutableMap<String, Long> BROKER_DOMAIN_ID_MAP = ImmutableMap.of();
    private static ImmutableMap<Long, String> BROKER_SIGN_SALT_MAP = ImmutableMap.of();

    private final IGrpcClientPool pool;

    private final String brokerServerChannelName;

    public AppSignatureCheckInterceptor(IGrpcClientPool pool, String brokerServerChannelName) {
        this.pool = pool;
        this.brokerServerChannelName = brokerServerChannelName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ((request.getAttribute(BrokerCoreConstants.PLATFORM_REQUEST_ATTR)).equals(RequestPlatform.MOBILE)
                && request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            Object skipSigCheck = request.getAttribute(BrokerCoreConstants.SKIP_APP_SIGNATURE_CHECK_ATTR);
            if (skipSigCheck != null && skipSigCheck.equals(Boolean.TRUE)) {
                return true;
            }

            String sign = request.getParameter(SIGN_PARAM_NAME);
            List<String> paramNames = Collections.list(request.getParameterNames());
            if (Strings.isNullOrEmpty(sign) || paramNames.size() <= 0) {
                throw new BrokerException(BrokerErrorCode.PARAM_INVALID);
            }

            paramNames.sort(Comparator.naturalOrder());
            String requestParams = paramNames.stream().filter(key -> !key.equals(SIGN_PARAM_NAME))
                    .map(key -> key + "=" + request.getParameter(key)).collect(Collectors.joining(""));
            Long orgId = -1L;
            if (request.getAttribute(BrokerCoreConstants.ORG_ID_ATTR) != null) {
                orgId = Longs.tryParse(request.getAttribute(BrokerCoreConstants.ORG_ID_ATTR).toString());
            } else {
                for (String domain : BROKER_DOMAIN_ID_MAP.keySet()) {
                    if (request.getServerName().endsWith(domain)) {
                        orgId = BROKER_DOMAIN_ID_MAP.get(domain);
                    }
                }
            }
            if (!sign.equals(Hashing.md5().hashString(requestParams + BROKER_SIGN_SALT_MAP.getOrDefault(orgId, DEFAULT_MD5_SALT), Charsets.UTF_8).toString())) {
                log.error("app request failed to pass sign verification, params:{}, sign:{}",
                        requestParams, sign);
                throw new BrokerException(BrokerErrorCode.REQUEST_INVALID);
            }
        }
        return true;
    }

    @EventListener(value = {ContextRefreshedEvent.class})
    @Scheduled(cron = "0 0/5 * * * ?")
    private void refreshBrokerInfo() {
        BrokerServiceGrpc.BrokerServiceBlockingStub stub = BrokerServiceGrpc.newBlockingStub(pool.borrowChannel(brokerServerChannelName));
        QueryBrokerResponse response = stub.queryBrokers(QueryBrokerRequest.getDefaultInstance());
        List<Broker> brokerList = response.getBrokersList();

        Map<String, Long> domainMap = Maps.newHashMap();
        Map<Long, String> signSaltMap = Maps.newHashMap();
        if (brokerList.size() > 0) {
            for (Broker broker : brokerList) {
                if (broker.getStatus() == 1) {
                    broker.getApiDomainsList().forEach(domain -> domainMap.put(domain, broker.getOrgId()));
                    signSaltMap.put(broker.getOrgId(), broker.getAppRequestSignSalt());
                }
            }
            BROKER_DOMAIN_ID_MAP = ImmutableMap.copyOf(domainMap);
            BROKER_SIGN_SALT_MAP = ImmutableMap.copyOf(signSaltMap);
        }
    }

}
