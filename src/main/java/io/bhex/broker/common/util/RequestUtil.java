/*
 ************************************
 * @项目名称: broker
 * @文件名称: RequestUtil
 * @Date 2018/05/22
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */
package io.bhex.broker.common.util;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import com.google.common.net.InetAddresses;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Request Util
 *
 * @author peiwei.ren
 */
@Slf4j
public class RequestUtil {

    private static final String DEBUG_REQUEST_ID = "debugRequestId";

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (Strings.nullToEmpty(ip).contains("127.0.0.1")) {
            printRequest(request, "");
        }
        if (!Strings.isNullOrEmpty(ip) && ip.contains(",")) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                if (!Strings.isNullOrEmpty(ips[index]) || !ips[index].trim().equals("127.0.0.1")) {
                    ip = ips[index].trim();
                    break;
                }
            }
        }
        if (!Strings.isNullOrEmpty(ip) && !ip.trim().equals("127.0.0.1") && InetAddresses.isInetAddress(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (!Strings.isNullOrEmpty(ip) && !ip.trim().equals("127.0.0.1") && InetAddresses.isInetAddress(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static void printRequest(HttpServletRequest request, String uuid) {
        try {
            String serverName = request.getServerName();
            String requestUrl = request.getRequestURI();
            String debugRequestId = request.getParameter(DEBUG_REQUEST_ID);
            if (Strings.isNullOrEmpty(debugRequestId) && !Strings.isNullOrEmpty(uuid)) {
                debugRequestId = uuid;
            }
            if (Strings.isNullOrEmpty(debugRequestId)) {
                debugRequestId = RandomStringUtils.random(16);
            }
            Enumeration<String> parameterNames = request.getParameterNames();
            JsonObject paramJson = new JsonObject();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                paramJson.addProperty(paramName, Strings.nullToEmpty(request.getParameter(paramName)));
            }
            Enumeration<String> headerNames = request.getHeaderNames();
            JsonObject headerJson = new JsonObject();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headerJson.addProperty(headerName, Strings.nullToEmpty(request.getHeader(headerName)));
            }
            JsonObject cookieJson = new JsonObject();
            if (request.getCookies() != null) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    cookieJson.addProperty(cookie.getName(), cookie.getValue());
                }
            }
            JsonObject dataObj = new JsonObject();
            dataObj.addProperty("serverName", serverName);
            dataObj.addProperty("requestUrl", requestUrl);
            dataObj.addProperty("debugRequestId", debugRequestId);
            dataObj.add("paramJson", paramJson);
            dataObj.add("headerJson", headerJson);
            dataObj.add("cookieJson", cookieJson);
            log.info("debugRequestInfo: {}", JsonUtil.defaultGson().toJson(dataObj));
        } catch (Exception e) {
            // ignore
        }
    }

}


