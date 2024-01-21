/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.entity
 *@Date 2018/6/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

public enum RequestPlatform {
    /**
     * 这里里面的数据，需要和proto中定义的Platform保持一致！！！
     * 谨记！！！
     */
    /**
     * 用户在PC端操作
     */
    PC,
    /**
     * 用户在手机端操作
     */
    MOBILE,
    /**
     * 用户在H5界面操作
     */
    H5,
    /**
     * 用户通过OPENAPI操作
     */
    OPENAPI,
    WE_CHAT,
    /**
     * 通过OAUTH操作用户
     */
    OAUTH_API,
    /**
     * ORG_API操作用户
     */
    ORG_API;
}
