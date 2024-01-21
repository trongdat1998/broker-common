/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.api.client.geetest
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.api.client.geetest;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class DkGeeTestVerifyResponse {
    /**
     * 详细见错误码文档
     */
    private Integer status;
    private String message;
    private String captcha;
    @SerializedName("risk_code")
    private List<Integer> riskCode;
    @SerializedName("risk_level")
    private Integer riskLevel;
    private String challenge;
    @SerializedName("finger_print")
    private String fingerPrint;
    /**
     * 返回client_type, ip, refer, user_agent4个字段(非必须)
     */
    private Map<String,String> info;
    /**
     * 针对当前业务定制的精准分析引擎识别结果字符串数组（非必须）
     */
    @SerializedName("private_action")
    private List<String> privateAction;
    /**
     * 处理流流分支命中后对应的action值，和入参flow_id对应（非必须）
     */
    private String action;
    /**
     * 当处理流执行出错时显示的错误信息（非必须）
     */
    @SerializedName("action_error")
    private String actionError;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCaptcha() {
        return captcha;
    }

    public List<Integer> getRiskCode() {
        return riskCode;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public List<String> getPrivateAction() {
        return privateAction;
    }

    public String getAction() {
        return action;
    }

    public String getActionError() {
        return actionError;
    }
}
