/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.alibaba
 *@Date 2019/1/14
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.alibaba;

import lombok.Data;

@Data
public class AliSigCheckProperties {

    private String regionId;
    private String accesskeyId;
    private String accessKeySecret;

}
