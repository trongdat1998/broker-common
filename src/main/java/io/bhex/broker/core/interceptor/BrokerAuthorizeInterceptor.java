/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.core
 *@Date 2018/11/12
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.interceptor;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.Hashing;

import com.beust.jcommander.internal.Lists;

import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.bhex.base.grpc.client.channel.IGrpcClientPool;
import io.bhex.broker.common.entity.CommonConstants;
import io.bhex.broker.common.entity.Header;
import io.bhex.broker.common.entity.RequestPlatform;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.redis.StringKeySerializer;
import io.bhex.broker.common.util.CookieUtil;
import io.bhex.broker.common.util.HeaderUtil;
import io.bhex.broker.common.util.RequestUtil;
import io.bhex.broker.core.domain.BrokerCoreConstants;
import io.bhex.broker.core.domain.VerifyLogin;
import io.bhex.broker.grpc.security.SecurityServiceGrpc;
import io.bhex.broker.grpc.security.SecurityVerifyLoginRequest;
import io.bhex.broker.grpc.security.SecurityVerifyLoginResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrokerAuthorizeInterceptor implements HandlerInterceptor, InitializingBean {

    private static final String USER_ID_MDC_KEY = "userId";

    private static final List<String> BROKER_COOKIE_NAMES = Lists.newArrayList(
            BrokerCoreConstants.AU_TOKEN_COOKIE_NAME,
            BrokerCoreConstants.USER_ID_COOKIE_NAME,
            BrokerCoreConstants.ACCOUNT_ID_COOKIE_NAME,
            BrokerCoreConstants.CSRF_TOKEN_PARAM_NAME);

    private static final Integer TOKEN_EXPIRED_THRESHOLD = 30 * 60; // token有效续期时间
    private static final Integer DEFAULT_PC_TOKEN_EXPIRED_THRESHOLD = 22 * 60 * 60; // token有效续期时间
    private static final Integer DEFAULT_APP_TOKEN_EXPIRED_THRESHOLD = 23 * 60 * 60; // token有效续期时间
    private static final Integer LOCAL_CACHE_TOKEN_EXPIRE_AFTER = 24 * 60 * 60; // pc token有效时间
    private static final Integer DEFAULT_PC_TOKEN_EXPIRE_AFTER = 24 * 60 * 60; // pc token有效时间
    private static final Integer DEFAULT_APP_TOKEN_EXPIRE_AFTER = 24 * 60 * 60; // app token有效时间
    private static final Integer DEFAULT_APP_COOKIE_EXPIRE = 24 * 60 * 60; // 24小时有效

    private static final Integer DEFAULT_PC_COOKIE_EXPIRE = -1; // 默认关闭浏览器清楚cookie

    private static final Cache<String, Long> DEFAULT_LOCAL_TOKEN_CACHE = CacheBuilder.newBuilder()
            .maximumSize(20000) // 一个POD最多两万用户同时在线
            .expireAfterAccess(LOCAL_CACHE_TOKEN_EXPIRE_AFTER, TimeUnit.SECONDS)
            .build();

    private final RedisTemplate<String, Long> redisTemplate;

    private final IGrpcClientPool pool;

    private final String securityChannelName;

    @Setter
    private Cache<String, Long> localTokenCache;

    @Setter
    private Integer pcTokenExpiredAfter;

    @Setter
    private Integer appTokenExpiredAfter;

    @Setter
    private Integer pcCookieExpire;

    @Setter
    private Integer appCookieExpire;

    @Setter
    private Boolean checkCsrfTokenStrictly;

    @Setter
    private Integer pcTokenExpiredThreshold;

    @Setter
    private Integer appTokenExpiredThreshold;

    public BrokerAuthorizeInterceptor(RedisTemplate<String, Long> redisTemplate, IGrpcClientPool pool, String securityChannelName) {
        redisTemplate.setKeySerializer(new StringKeySerializer(BrokerCoreConstants.AUTHORIZE_KEY_PREFIX));
        this.redisTemplate = redisTemplate;
        this.pool = pool;
        this.securityChannelName = securityChannelName;
    }

    @Override
    public void afterPropertiesSet() {
        if (localTokenCache == null) {
            localTokenCache = DEFAULT_LOCAL_TOKEN_CACHE;
        }
        if (pcTokenExpiredAfter == null) {
            pcTokenExpiredAfter = DEFAULT_PC_TOKEN_EXPIRE_AFTER;
        }
        if (appTokenExpiredAfter == null) {
            appTokenExpiredAfter = DEFAULT_APP_TOKEN_EXPIRE_AFTER;
        }
        if (pcCookieExpire == null) {
            pcCookieExpire = DEFAULT_PC_COOKIE_EXPIRE;
        }
        if (appCookieExpire == null) {
            appCookieExpire = DEFAULT_APP_COOKIE_EXPIRE;
        }
        if (checkCsrfTokenStrictly == null) {
            checkCsrfTokenStrictly = false;
        }
        if (pcTokenExpiredThreshold == null) {
            pcTokenExpiredThreshold = DEFAULT_PC_TOKEN_EXPIRED_THRESHOLD;
        }
        if (appTokenExpiredThreshold == null) {
            appTokenExpiredThreshold = DEFAULT_APP_TOKEN_EXPIRED_THRESHOLD;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        VerifyLogin verifyLogin = handlerMethod.getMethodAnnotation(VerifyLogin.class);
        if (verifyLogin == null) {
            verifyLogin = handlerMethod.getBeanType().getAnnotation(VerifyLogin.class);
        }
        if (verifyLogin == null) {
            return true;
        }

        Header header = (Header) request.getAttribute(BrokerCoreConstants.HEADER_REQUEST_ATTR);
        String auToken = CookieUtil.getValue(request, BrokerCoreConstants.AU_TOKEN_COOKIE_NAME);

        if (!Strings.isNullOrEmpty(auToken)) {
            String md5Token = Hashing.md5().hashString(auToken, Charsets.UTF_8).toString();
            Long l2CacheUserId = null;

            try {
                l2CacheUserId = localTokenCache.get(md5Token, () -> {
                    Long cachedUserId = null;
                    try {
                        cachedUserId = redisTemplate.opsForValue().get(md5Token);
//                        if (cachedUserId != null && redisTemplate.getExpire(md5Token) <= TOKEN_EXPIRED_THRESHOLD) {
//                            redisTemplate.expire(md5Token, header.getPlatform() == RequestPlatform.MOBILE ? appTokenExpiredAfter : pcTokenExpiredAfter, TimeUnit.SECONDS);
//                        }
                        if (cachedUserId == null) {
                            cachedUserId = -1L;
                        }
                    } catch (Exception e) {
                        /*
                         * 这里有异常可能是因为redis的问题，先返回0，让程序走鉴权，不要返回鉴权失败
                         */
                        log.error("set expire time with md5Token:{} error, cachedUserId:{}", md5Token, cachedUserId, e);
                        cachedUserId = 0L;
                    }
                    return cachedUserId;
                });
            } catch (Exception e) {
                // 这里捕获异常也要继续走鉴权，因为不知道是不是程序内部bug导致的
                log.error("get cachedUserId from redis with md5Token:{} error", md5Token);
                l2CacheUserId = 0L;
                // ignore
            }
            if (l2CacheUserId != null && l2CacheUserId != -1) {
                io.bhex.broker.grpc.common.Header grpcHeader = HeaderUtil.convertGrpcHeader(header);
                SecurityVerifyLoginRequest verifyLoginRequest = SecurityVerifyLoginRequest.newBuilder()
                        .setHeader(grpcHeader)
                        .setToken(auToken)
                        .build();
                SecurityServiceGrpc.SecurityServiceBlockingStub stub = SecurityServiceGrpc.newBlockingStub(pool.borrowChannel(securityChannelName));
                SecurityVerifyLoginResponse grpcResponse = stub.verifyLogin(verifyLoginRequest);
                if (grpcResponse.getRet() == 0 && grpcResponse.getUserId() > 0) {
                    MDC.put(USER_ID_MDC_KEY, String.valueOf(grpcResponse.getUserId()));

                    String cookieUserId = CookieUtil.getValue(request, CommonConstants.USER_ID_COOKIE_NAME);
                    if (Strings.isNullOrEmpty(cookieUserId) || !cookieUserId.equals(String.valueOf(grpcResponse.getUserId()))
                            || (l2CacheUserId > 0 && !l2CacheUserId.toString().equals(String.valueOf(grpcResponse.getUserId())))) {
                        String uuid = UUID.randomUUID().toString();
                        log.error("[FatalError]!!! uuid:{} orgId:{} cookieUserId:{} authorizedUserId:{}, cachedUserId:{} not equals, please check, ",
                                uuid, header.getOrgId(), cookieUserId, grpcResponse.getUserId(), l2CacheUserId);
                        RequestUtil.printRequest(request, uuid);
                        if (header.getOrgId() != 7154) {
                            if (request.getCookies() != null && request.getCookies().length > 0) {
                                for (Cookie cookie : request.getCookies()) {
                                    if (BROKER_COOKIE_NAMES.contains(cookie.getName())) {
                                        CookieUtil.clear(response, header.getDomain(), cookie.getName());
                                    }
                                }
                            }
                            redisTemplate.delete(md5Token);
                            try {
                                new StringRedisTemplate(redisTemplate.getConnectionFactory()).convertAndSend("logout", md5Token);
                            } catch (Exception e) {
                                log.warn("redisTemplate send a 'logout' message: {}", md5Token);
                            }
                            throw new BrokerException(BrokerErrorCode.AUTHORIZE_ERROR);
                        }
                    }

                    String cookieCsrfToken = CookieUtil.getValue(request, BrokerCoreConstants.CSRF_TOKEN_PARAM_NAME);
                    String requestCsrfToken = request.getParameter(BrokerCoreConstants.CSRF_TOKEN_PARAM_NAME);
                    if (Strings.isNullOrEmpty(requestCsrfToken)) {
                        requestCsrfToken = Strings.nullToEmpty(request.getHeader(BrokerCoreConstants.CSRF_TOKEN_PARAM_NAME));
                    }

                    if (Strings.isNullOrEmpty(requestCsrfToken)) {
                        requestCsrfToken = Strings.nullToEmpty(request.getHeader(BrokerCoreConstants.CSRF_TOKEN_PARAM_NAME_V1));
                    }

                    if (!Strings.isNullOrEmpty(cookieCsrfToken) && !cookieCsrfToken.equals(Strings.nullToEmpty(requestCsrfToken))) {
                        if (header.getPlatform() == RequestPlatform.PC) {
                            log.warn("[WARN] requestUri:{}, userId:{} cookieCsrfToken {} and requestCsrfToken {} equals to cookieCsrfToken",
                                    request.getRequestURI(), l2CacheUserId, cookieCsrfToken, requestCsrfToken);

                            if (checkCsrfTokenStrictly) {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                return false;
                            }
                        }
                    }
                    request.setAttribute(BrokerCoreConstants.HEADER_REQUEST_ATTR, header.toBuilder().userId(grpcResponse.getUserId()).build());
                    if (header.getPlatform().equals(RequestPlatform.MOBILE)) {
                        try {
                            if (redisTemplate.getExpire(md5Token) <= appTokenExpiredThreshold) {
                                redisTemplate.expire(md5Token, appTokenExpiredAfter, TimeUnit.SECONDS);
                            }
                        } catch (Exception e) {
                            // ignore
                        }
                        for (Cookie cookie : request.getCookies()) {
                            CookieUtil.create(response, header.getDomain(), cookie.isHttpOnly(), cookie.getName(), cookie.getValue(), appCookieExpire);
                        }
                    } else if (header.getPlatform().equals(RequestPlatform.PC)) {
                        try {
                            if (redisTemplate.getExpire(md5Token) <= pcTokenExpiredThreshold) {
                                redisTemplate.expire(md5Token, pcTokenExpiredAfter, TimeUnit.SECONDS);
                            }
                        } catch (Exception e) {
                            // ignore
                        }
                        // 如果cookie设置了有效期，那么进行续期
                        if (request.getCookies() != null && request.getCookies().length > 0 && pcCookieExpire > 0) {
                            for (Cookie cookie : request.getCookies()) {
                                CookieUtil.create(response, header.getDomain(), cookie.isHttpOnly(), cookie.getName(), cookie.getValue(), pcCookieExpire);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        if (verifyLogin.interrupt()) {
            if (request.getCookies() != null && request.getCookies().length > 0) {
                for (Cookie cookie : request.getCookies()) {
                    if (BROKER_COOKIE_NAMES.contains(cookie.getName())) {
                        CookieUtil.clear(response, header.getDomain(), cookie.getName());
                    }
                }
            }
            throw new BrokerException(BrokerErrorCode.AUTHORIZE_ERROR);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(USER_ID_MDC_KEY);
    }

}
