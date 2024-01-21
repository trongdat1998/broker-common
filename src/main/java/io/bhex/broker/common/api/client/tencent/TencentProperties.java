package io.bhex.broker.common.api.client.tencent;

import lombok.Data;

/**
 * @author JinYuYuan
 * @description
 * @date 2020-10-16 10:10
 */
@Data
public class TencentProperties {
    private String secretId = "";
    private String secretKey = "";
    private String source = "market";
    private String idCardVerifyUrl = "";
}
