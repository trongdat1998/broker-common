package io.bhex.broker.common.api.client.tencent;

import lombok.Builder;
import lombok.Data;

/**
 * @author JinYuYuan
 * @description
 * @date 2020-10-16 10:16
 */
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class TencentIDCardVerifyResponse {
    public Integer code;
    public String message;
    public Integer result;
    public String remark;
}

