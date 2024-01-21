package io.bhex.broker.common.api.client.jiean;

/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.jiean
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
public enum BankCardVerifyType {

    CARD_2("CARD2N"),
    CARD_3("CARD3CN"),
    CARD_4("CARD4");

    private String value;

    BankCardVerifyType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
