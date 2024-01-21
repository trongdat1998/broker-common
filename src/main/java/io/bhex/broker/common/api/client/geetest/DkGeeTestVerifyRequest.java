/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.geetest
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.geetest;

import lombok.Builder;
import lombok.Data;

import javax.json.JsonObject;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class DkGeeTestVerifyRequest {

    /**
     * 分组ID(程序内必须)
     */
    private transient String id;

    /**
     * 查询结果的凭证, 从前一个接口返回 (必须)
     */
    private String sessionId;
    /**
     * 用户的站内ID（必须）
     */
    private String userId;
    /**
     * 用户的邮箱
     */
    private String userEmail;
    /**
     * 用户的手机号码，支持MD5后的32位小写字符串
     */
    private String phoneNum;
    /**
     * 场景关键字，参考场景枚举（必须）
     */
    private String scene;
    /**
     * 其他有助于风险分析的业务数据
     */
    private JsonObject attr;
    /**
     * 处理流id，在account的处理流查看页面可以找到该id。未启用处理引擎的情况下不需要传该字段。
     */
    private String flowId;

}
