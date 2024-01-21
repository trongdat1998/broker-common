/**********************************
 * @项目名称: broker-common
 * @文件名称: io.bhex.broker.core.domain
 * @Date 2018/11/12
 * @Author peiwei.ren@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.domain;

import io.bhex.broker.grpc.common.BasicRet;

import java.util.regex.Pattern;

public class BrokerCoreConstants {

    public static final Pattern URL_PREFIX_PATTERN = Pattern.compile("^/(m/|m|s_|m/s_|ms_)?api/");

    /**
     * App Request Url prefix
     */
    public static final Pattern APP_REQUEST_URL_PREFIX = Pattern.compile("^/(m/|m|m/s_|ms_)api/");

    public static final String ORIGINAL_REQUEST_URI_ATTR = ".ORIGINAL_REQUEST_URI_ATTR";

    public static final String PLATFORM_REQUEST_ATTR = ".REQUEST_PLATFORM";

    public static final String HEADER_REQUEST_ATTR = ".HEADER_REQUEST_ATTR";

    public static final String SKIP_APP_SIGNATURE_CHECK_ATTR = ".SKIP_APP_SIGNATURE_CHECK_ATTR";

    public static final String ORG_ID_ATTR = ".ORG_ID_ATTR";

    public static final String AUTHORIZE_KEY_PREFIX = "authorize:";

    public static final String USER_ID_COOKIE_NAME = "user_id";

    public static final String AU_TOKEN_COOKIE_NAME = "au_token";

    public static final String ACCOUNT_ID_COOKIE_NAME = "account_id";

    public static final String REQUEST_ID_HEADER_NAME = "request-id";

    public static final String CSRF_TOKEN_PARAM_NAME = "c_token";

    public static final String CSRF_TOKEN_PARAM_NAME_V1 = "X-C-TOKEN";

    public static final String BROKER_SERVER_CHANNEL_NAME = "brokerServer";

    public static final BasicRet SUCCESS_RET = BasicRet.newBuilder().setCode(0).build();

}
