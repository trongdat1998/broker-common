package io.bhex.broker.common.api.client.tencent;

import lombok.Builder;
import lombok.Data;

/**
 * @author JinYuYuan
 * @description
 * @date 2020-10-16 10:14
 */
@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class TencentIDCardVerifyRequest {
    public String certcode;
    public String name;
}
