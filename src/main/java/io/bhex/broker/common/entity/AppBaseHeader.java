/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.entity
 *@Date 2018/8/10
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class AppBaseHeader {

    private String app;
    private String appId;
    private String appVersion;
    private String nett;
    private String channel;
    private String osType;
    private String osVersion;
    private String imsi;
    private String imei;
    private String deviceToken;

}
