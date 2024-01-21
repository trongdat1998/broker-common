/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.common.entity
 *@Date 2018/6/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class GrpcClientProperties {

    private Long futureTimeout = 500L;
    private Long stubDeadline = 500L;
    private Long shortStubDeadline = 500L;
    private List<GrpcChannelInfo> channelInfo = Lists.newArrayList();
}
