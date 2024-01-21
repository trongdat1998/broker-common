/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.jiean
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.jiean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "verify")
public class BankCardVerifyResponse {

    private String versionId;

    private String custId;

    private String ordId;

    private String transType;

    private String merPriv;

    private String jsonStr;

    private String respCode;

    private String respDesc;

    private String resTxnId;

    private String macStr;

}
