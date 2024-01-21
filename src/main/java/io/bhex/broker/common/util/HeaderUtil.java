/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.util
 *@Date 2018/6/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.util;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.common.net.HttpHeaders;
import com.google.common.net.InetAddresses;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import io.bhex.broker.common.entity.AppBaseHeader;
import io.bhex.broker.common.entity.BHexAppHeaders;
import io.bhex.broker.common.entity.CommonConstants;
import io.bhex.broker.common.entity.Header;
import io.bhex.broker.common.entity.RequestPlatform;
import io.bhex.broker.grpc.common.Platform;

public class HeaderUtil {

    public static final String CHANNEL_NAME = "channel";
    public static final String SOURCE_NAME = "source";
    public static final String UTM_NAME = "utm";

    public static Header buildFromHttp(HttpServletRequest request) {
        String channel = getValueFromRequest(request, CHANNEL_NAME);
        String source = getValueFromRequest(request, SOURCE_NAME);
        if (Strings.isNullOrEmpty(source)) {
            source = getValueFromRequest(request, UTM_NAME);
        }
        return Header.builder()
                .serverName(request.getServerName())
                .serverUri(request.getRequestURI())
                .remoteIp(RequestUtil.getRemoteIp(request))
                .userAgent(Strings.nullToEmpty(request.getHeader(HttpHeaders.USER_AGENT)))
                .referer(Strings.nullToEmpty(request.getHeader(HttpHeaders.REFERER)))
                .securityToken(Strings.isNullOrEmpty(CookieUtil.getValue(request, CommonConstants.AU_TOKEN_COOKIE_NAME)) ?
                        "" : Hashing.md5().hashString(Strings.nullToEmpty(CookieUtil.getValue(request, CommonConstants.AU_TOKEN_COOKIE_NAME)), Charsets.UTF_8).toString())
                .uncheckedUserId(Strings.nullToEmpty(CookieUtil.getValue(request, CommonConstants.USER_ID_COOKIE_NAME)))
                .channel(Strings.nullToEmpty(channel))
                .source(Strings.nullToEmpty(source))
                .language(LocaleContextHolder.getLocale().toString())
                .requestTime(System.currentTimeMillis())
                .uuid(Optional.of(getValueFromRequest(request, "__uuid")).orElse(getValueFromRequest(request, "_uuid")))
                .build();
    }

    public static String getValueFromRequest(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        if (Strings.isNullOrEmpty(paramValue)) {
            paramValue = CookieUtil.getValue(request, paramName);
            if (Strings.isNullOrEmpty(paramValue)) {
                paramValue = request.getHeader(paramName);
            }
        }
        return Strings.nullToEmpty(paramValue);
    }

    public static AppBaseHeader buildAppRequestHeaderFromRequest(HttpServletRequest request) {
//        return AppBaseHeader.builder()
//                .app(getAppBaseHeaderValue(request, BHexAppHeaders.APP))
//                .appId(getAppBaseHeaderValue(request, BHexAppHeaders.APP_ID))
//                .appVersion(getAppBaseHeaderValue(request, BHexAppHeaders.APP_VERSION))
//                .nett(getAppBaseHeaderValue(request, BHexAppHeaders.NETT))
//                .channel(getAppBaseHeaderValue(request, BHexAppHeaders.CHANNEL))
//                .osType(getAppBaseHeaderValue(request, BHexAppHeaders.OS_TYPE))
//                .osVersion(getAppBaseHeaderValue(request, BHexAppHeaders.OS_VERSION))
//                .imsi(getAppBaseHeaderValue(request, BHexAppHeaders.IMSI))
//                .imei(getAppBaseHeaderValue(request, BHexAppHeaders.IMEI))
//                .deviceToken(getAppBaseHeaderValue(request, BHexAppHeaders.DEVICE_TOKEN))
//                .build();
        // replace getAppBaseHeaderValue with getValueFromRequest
        return AppBaseHeader.builder()
                .app(getValueFromRequest(request, BHexAppHeaders.APP))
                .appId(getValueFromRequest(request, BHexAppHeaders.APP_ID))
                .appVersion(getValueFromRequest(request, BHexAppHeaders.APP_VERSION))
                .nett(getValueFromRequest(request, BHexAppHeaders.NETT))
                .channel(getValueFromRequest(request, BHexAppHeaders.CHANNEL))
                .osType(getValueFromRequest(request, BHexAppHeaders.OS_TYPE))
                .osVersion(getValueFromRequest(request, BHexAppHeaders.OS_VERSION))
                .imsi(getValueFromRequest(request, BHexAppHeaders.IMSI))
                .imei(getValueFromRequest(request, BHexAppHeaders.IMEI))
                .deviceToken(getValueFromRequest(request, BHexAppHeaders.DEVICE_TOKEN))
                .build();
    }

    @Deprecated
    private static String getAppBaseHeaderValue(HttpServletRequest request, String headerName) {
        String headerValue = request.getParameter(headerName);
        if (Strings.isNullOrEmpty(headerValue)) {
            headerValue = Strings.nullToEmpty(request.getHeader(headerName));
        }
        return headerValue;
    }

    private static final String[] MOBILE_KEYWORDS = {
            "android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser", "iuc", "wget", "curl", "axel", "xiaomi",
            "redmi", "blackberry", "nexus", "htc", "huawei", "kindle", "lenovo", "lg", "motorola", "nokia",
            "oneplus", "playstation", "samsung", "xperia", "zte", "phone"};

    public static boolean isFromMobile(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent.trim().isEmpty()) {
            return false;
        }
        if (userAgent.contains("Windows NT")) {
            return false;
        }
        if (userAgent.contains("Macintosh")) {
            return false;
        }
        userAgent = userAgent.toLowerCase();
        for (String keyword : MOBILE_KEYWORDS) {
            if (userAgent.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static String getRemoteIp(HttpServletRequest request) {
        String ip;
        ip = request.getHeader("X-Forwarded-For");
        if (!Strings.isNullOrEmpty(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if (!Strings.isNullOrEmpty(ip) && InetAddresses.isInetAddress(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (!Strings.isNullOrEmpty(ip) && InetAddresses.isInetAddress(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static io.bhex.broker.grpc.common.Header convertGrpcHeader(Header header) {
        io.bhex.broker.grpc.common.Header.Builder headerBuilder = io.bhex.broker.grpc.common.Header.newBuilder();
        headerBuilder.setOrgId(header.getOrgId())
                .setUserId(header.getUserId() == null ? 0L : header.getUserId())
                .setUserAgent(Strings.nullToEmpty(header.getUserAgent()))
                .setReferer(Strings.nullToEmpty(header.getReferer()))
                .setRemoteIp(Strings.nullToEmpty(header.getRemoteIp()))
                // .setPlatform(header.getPlatform() == RequestPlatform.PC ? Platform.PC : Platform.MOBILE)
                .setPlatform(Platform.valueOf(header.getPlatform().name()))
                .setLanguage(Strings.nullToEmpty(header.getLanguage()))
                .setRequestId(Strings.nullToEmpty(header.getRequestId()))
                .setRequestUri(Strings.nullToEmpty(header.getServerUri()))
                .setChannel(Strings.nullToEmpty(header.getChannel()))
                .setSource(Strings.nullToEmpty(header.getSource()))
                .setServerName(Strings.nullToEmpty(header.getServerName()))
                .setRequestTime(header.getRequestTime() == null ? System.currentTimeMillis() : header.getRequestTime())
                .setUuid(Strings.nullToEmpty(header.getUuid()));
        if (header.getPlatform() != RequestPlatform.PC && header.getAppBaseHeader() != null) {
            io.bhex.broker.grpc.common.AppBaseHeader baseHeader = io.bhex.broker.grpc.common.AppBaseHeader.newBuilder()
                    .setApp(Strings.nullToEmpty(header.getAppBaseHeader().getApp()))
                    .setAppId(Strings.nullToEmpty(header.getAppBaseHeader().getAppId()))
                    .setAppVersion(Strings.nullToEmpty(header.getAppBaseHeader().getAppVersion()))
                    .setNett(Strings.nullToEmpty(header.getAppBaseHeader().getNett()))
                    .setChannel(Strings.nullToEmpty(header.getAppBaseHeader().getChannel()))
                    .setOsType(Strings.nullToEmpty(header.getAppBaseHeader().getOsType()))
                    .setOsVersion(Strings.nullToEmpty(header.getAppBaseHeader().getOsVersion()))
                    .setImei(Strings.nullToEmpty(header.getAppBaseHeader().getImei()))
                    .setImsi(Strings.nullToEmpty(header.getAppBaseHeader().getImsi()))
                    .setChannel(Strings.nullToEmpty(header.getChannel()))
                    .build();
            headerBuilder.setAppBaseHeader(baseHeader);
        }
        return headerBuilder.build();
    }

}
