package io.bhex.broker.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.entity
 *@Date 2018/6/10
 *@Author peiwei.ren@bhex.io
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrpcChannelInfo {

    private String channelName;
    private String host;
    private Integer port;
    
    @Deprecated
    private Boolean useSsl = false;
    @Deprecated
    private String keyFilePath = "";
    @Deprecated
    private String crtFilePath = "";

}
