/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.geetest
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.geetest;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class GeeTestProperties {

    private List<GeeTestConfig> configs = Lists.newArrayList();
    private String geeTestUrl = "";
    private Boolean crashContinue = false;
    private Integer highRiskLevel = 7;
    private Boolean filterHighRiskRequest = false;
    private Integer connTimeout = 0;
    private Integer readTimeout = 0;

}
