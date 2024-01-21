package io.bhex.broker.core.interceptor;

import javax.servlet.http.HttpServletRequest;

import io.bhex.broker.common.entity.Header;
import io.bhex.broker.core.domain.BrokerCoreConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 基于用户限速拦截
 *
 * @author lizhen
 * @date 2018-11-22
 */
@Deprecated
public class UserFlowControlInterceptor extends AbstractFlowControlInterceptor {

    /**
     * 默认的用户限速配置（每秒N次）
     */
    private int requestLimit = 20;

    /**
     * redis用户限速配置的key
     */
    private static final String USER_LIMIT_CONFIG_KEY = "BROKER::USER_LIMIT::CONFIG::MAP";

    /**
     * redis用户请求次数的key
     */
    private static final String USER_REQUEST_COUNT_KEY = "BROKER::USER_REQUEST::COUNT::%s";

    public UserFlowControlInterceptor(StringRedisTemplate redisTemplate, int requestLimit) {
        super(redisTemplate, USER_LIMIT_CONFIG_KEY);
        this.requestLimit = requestLimit;
    }

    @Override
    protected LimitInfo getLimitInfo(HttpServletRequest request) {
        Header header = (Header) request.getAttribute(BrokerCoreConstants.HEADER_REQUEST_ATTR);
        Long userId = header.getUserId();
        if (userId == null || userId <= 0) {
            return null;
        }
        Integer userLimit = localLimitConfig.get(String.valueOf(userId));
        if (userLimit == null) {
            userLimit = requestLimit * REQUEST_STATISTIC_FACTOR;
        }
        return new LimitInfo(userLimit, String.format(USER_REQUEST_COUNT_KEY, userId));
    }
}