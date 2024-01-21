/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.core.domain
 *@Date 2018/11/13
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class BrokerInfo {

    private Long id;
    private Long orgId;
    private String brokerName;
    private String signName;
    private String apiDomains;
    private Integer status;

}
