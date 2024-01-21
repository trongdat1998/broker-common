package io.bhex.broker.core.interceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.common.exception.BrokerException;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 流量限速拦截
 *
 * @author lizhen
 * @date 2018-11-23
 */
@Deprecated
public abstract class AbstractFlowControlInterceptor implements HandlerInterceptor {

    /**
     * 限速配置本地缓存过期时间
     */
    private static final long LOCAL_LIMIT_CONFIG_EXPIRE = 60 * 1000;

    /**
     * 流量统计因子（N秒的统计区间）
     */
    protected static final int REQUEST_STATISTIC_FACTOR = 10;

    protected Map<String, Integer> localLimitConfig = Maps.newHashMap();

    protected class LimitInfo {
        private int limitCount;
        private String limitKey;

        public LimitInfo(int limitCount, String limitKey) {
            this.limitCount = limitCount;
            this.limitKey = limitKey;
        }

        @Override
        public String toString() {
            return "limitKey: " + limitKey + ", limitCount: " + limitCount;
        }
    }

    private StringRedisTemplate redisTemplate;

    private String limitConfigKey;

    protected abstract LimitInfo getLimitInfo(HttpServletRequest request);

    protected AbstractFlowControlInterceptor(StringRedisTemplate redisTemplate, String limitConfigKey) {
        this.redisTemplate = redisTemplate;
        this.limitConfigKey = limitConfigKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LimitInfo limitInfo = getLimitInfo(request);
        if (limitInfo == null) {
            return true;
        }
        if (limitInfo.limitCount <= 0) {
            return false;
        }
        long count;
        try {
            count = redisTemplate.opsForValue().increment(limitInfo.limitKey, 1);
            if (count > 1) {
                if (count > limitInfo.limitCount && RandomUtils.nextBoolean()) {
                    // 处理网络异常情况
                    if (redisTemplate.getExpire(limitInfo.limitKey) < 0) {
                        redisTemplate.expire(limitInfo.limitKey, REQUEST_STATISTIC_FACTOR, TimeUnit.SECONDS);
                    }
                }
            } else {
                redisTemplate.expire(limitInfo.limitKey, REQUEST_STATISTIC_FACTOR, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            return false;
        }
        if (count > limitInfo.limitCount) {
            throw new BrokerException(BrokerErrorCode.REQUEST_TOO_FAST);
        }
        return true;
    }

    @Scheduled(fixedRate = LOCAL_LIMIT_CONFIG_EXPIRE)
    public void refreshLocalConfig() {
        Map<Object, Object> redisConfigMap = redisTemplate.opsForHash().entries(limitConfigKey);
        if (CollectionUtils.isEmpty(redisConfigMap)) {
            localLimitConfig = Maps.newHashMap();
            return;
        }
        Map<String, Integer> configMap = Maps.newHashMap();
        for (Map.Entry<Object, Object> config : redisConfigMap.entrySet()) {
            configMap.put(config.getKey().toString(), Integer.parseInt(config.getValue().toString()));
        }
        localLimitConfig = configMap;
    }
}