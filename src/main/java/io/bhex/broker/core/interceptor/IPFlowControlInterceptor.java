package io.bhex.broker.core.interceptor;

import javax.servlet.http.HttpServletRequest;

import io.bhex.broker.common.util.HeaderUtil;
import io.bhex.broker.common.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 基于ip限速拦截
 *
 * @author lizhen
 * @date 2018-11-22
 */
@Deprecated
public class IPFlowControlInterceptor extends AbstractFlowControlInterceptor {

    /**
     * 默认的ip限速配置（每秒N次）
     */
    private int requestLimit = 1000;

    /**
     * redis ip限速配置的key
     */
    private static final String IP_LIMIT_CONFIG_KEY = "BROKER::IP_LIMIT::CONFIG::MAP";

    /**
     * redis ip请求次数的key
     */
    private static final String IP_REQUEST_COUNT_KEY = "BROKER::IP_REQUEST::COUNT::%s";

    public IPFlowControlInterceptor(StringRedisTemplate redisTemplate, int requestLimit) {
        super(redisTemplate, IP_LIMIT_CONFIG_KEY);
        this.requestLimit = requestLimit;
    }

    @Override
    protected LimitInfo getLimitInfo(HttpServletRequest request) {
        String ip = RequestUtil.getRemoteIp(request);
        Integer ipLimit = localLimitConfig.get(ip);
        if (ipLimit == null) {
            String[] ips = ip.split("\\.");
            if (ips.length == 4) {
                ipLimit = localLimitConfig.get(ips[0] + "." + ips[1] + "." + ips[2] + "." + "*");
            }

        }
        if (ipLimit == null) {
            ipLimit = requestLimit * REQUEST_STATISTIC_FACTOR;
        }
        return new LimitInfo(ipLimit, String.format(IP_REQUEST_COUNT_KEY, ip));
    }

    private String getBrowserIp(HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(address)) {
            address = request.getHeader("X-Real-IP");
        }

        if (!StringUtils.isBlank(address)) {
            String[] ips = address.split(",");
            if (ips.length > 0) {
                address = ips[0];
            }
            return address;
        }
        return request.getRemoteAddr();
    }
}