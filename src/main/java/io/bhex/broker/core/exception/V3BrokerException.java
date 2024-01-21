/*
 ************************************
 * @项目名称: broker
 * @文件名称: BrokerException
 * @Date 2018/05/22
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */
package io.bhex.broker.core.exception;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.bhex.broker.common.exception.BrokerErrorCode;
import io.bhex.broker.grpc.common.BasicRet;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
public class V3BrokerException extends RuntimeException {

    private static final long serialVersionUID = -2623309261327598087L;

    private int code;
    private List<String> messageArgs; // messageArgs used for format message
    private String showMe; // showMe will be followed tips for user

    public V3BrokerException(BrokerErrorCode brokerErrorCode) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
    }

    public V3BrokerException(BrokerErrorCode brokerErrorCode, List<String> messageArgs) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
        this.messageArgs = messageArgs;
    }

    public V3BrokerException(BrokerErrorCode brokerErrorCode, String showMe) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
        this.showMe = showMe;
    }

    public V3BrokerException(BrokerErrorCode brokerErrorCode, List<String> messageArgs, String showMe) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
        this.messageArgs = messageArgs;
        this.showMe = showMe;
    }

    public V3BrokerException(int code, String message) {
        super(Strings.nullToEmpty(message));
        this.code = code;
    }

    public V3BrokerException(int code, String message, List<String> messageArgs) {
        super(Strings.nullToEmpty(message));
        this.code = code;
        this.messageArgs = messageArgs;
    }

    public V3BrokerException(int code, String message, String showMe) {
        super(Strings.nullToEmpty(message));
        this.code = code;
        this.showMe = showMe;
    }

    public V3BrokerException(int code, String message, List<String> messageArgs, String showMe) {
        super(Strings.nullToEmpty(message));
        this.code = code;
        this.messageArgs = messageArgs;
        this.showMe = showMe;
    }

    public V3BrokerException(BasicRet basicRet) {
        super(Strings.nullToEmpty(basicRet.getMsg()));
        this.code = basicRet.getCode();
        this.messageArgs = basicRet.getArgsList();
        this.showMe = basicRet.getShowMe();
    }

    public List<String> getMessageArgs() {
        return messageArgs == null ? Lists.newArrayList() : messageArgs;
    }

    public String getShowMe() {
        return Strings.nullToEmpty(showMe);
    }

    @Override
    public String getMessage() {
        return "V3BrokerException:{"
                + "code:" + code
                + (Strings.isNullOrEmpty(super.getMessage()) ? "" : ", message:" + Strings.nullToEmpty(super.getMessage()))
                + (CollectionUtils.isEmpty(messageArgs) ? "" : ", messageArgs:" + String.join(",", getMessageArgs()))
                + (Strings.isNullOrEmpty(showMe) ? "" : ", showMe:" + getShowMe())
                + "}";
    }
}
