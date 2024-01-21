/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.entity
 *@Date 2018/6/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class Header {

    /**
     * Host
     */
    private final String serverName;
    /**
     * uri
     */
    private final String serverUri;
    /**
     * remoteIp
     */
    private final String remoteIp;
    /**
     * domain, value = (one in broker.getDomains)
     */
    private final String domain;
    private final String userAgent;
    private final String referer;
    /**
     * jwt token, value from au_token in Cookie
     * set a md5 value
     */
    private final String securityToken;
    /**
     * userId, value from userId in Cookie
     */
    private final String uncheckedUserId;
    /**
     * the real userId
     */
    private final Long userId;
    /**
     * value from request param, or cookie
     */
    private final String channel;
    /**
     * value from request param, or cookie
     */
    private final String source;
    /**
     * reference to a broker
     */
    private final Long orgId;
    /**
     * value from Accept-Language in Headers
     */
    private final String language;
    /**
     *
     */
    private final AppBaseHeader appBaseHeader;
    private final RequestPlatform platform;
    private final String requestId;
    private final Long requestTime;
    private final String uuid;

}
