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
package io.bhex.broker.common.exception;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.bhex.broker.common.util.JsonUtil;
import io.bhex.broker.grpc.common.BasicRet;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrokerException extends RuntimeException {

    private static final long serialVersionUID = -2623309261327598087L;

    private int code;
    private String errorMessage;
    private Object[] messageArgs; // messageArgs used for format message
    private String showMe; // showMe will be followed tils for user

    public BrokerException(BrokerErrorCode brokerErrorCode) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, String errorMessage) {
        super(Strings.isNullOrEmpty(errorMessage) ? brokerErrorCode.msg() : errorMessage);
        this.code = brokerErrorCode.code();
        this.errorMessage = errorMessage;
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, String errorMessage, String showMe) {
        super(Strings.isNullOrEmpty(errorMessage) ? brokerErrorCode.msg() : errorMessage);
        this.code = brokerErrorCode.code();
        this.errorMessage = errorMessage;
        this.showMe = showMe;
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, @Nullable Object[] messageArgs) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
        this.messageArgs = messageArgs;
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, @Nullable Object[] messageArgs, String showMe) {
        super(brokerErrorCode.msg());
        this.code = brokerErrorCode.code();
        this.messageArgs = messageArgs;
        this.showMe = showMe;
    }

    /**
     * This method will be removed after 1.5.* or 2.0.0
     */
    @Deprecated
    public BrokerException(BrokerErrorCode brokerErrorCode, String errorMessage, @Nullable Object[] messageArgs) {
        super(Strings.isNullOrEmpty(errorMessage) ? brokerErrorCode.msg() : errorMessage);
        this.code = brokerErrorCode.code();
        this.errorMessage = errorMessage;
        this.messageArgs = messageArgs;
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, Throwable cause) {
        super(brokerErrorCode.msg(), cause);
        this.code = brokerErrorCode.code();
    }

    public BrokerException(BrokerErrorCode brokerErrorCode, Throwable cause, String showMe) {
        super(brokerErrorCode.msg(), cause);
        this.code = brokerErrorCode.code();
        this.showMe = showMe;
    }

    /**
     * This method will be removed after 1.5.* or 2.0.0
     */
    public BrokerException(BrokerErrorCode brokerErrorCode, String errorMessage, Throwable cause) {
        super(Strings.isNullOrEmpty(errorMessage) ? brokerErrorCode.msg() : errorMessage);
        this.code = brokerErrorCode.code();
        this.errorMessage = errorMessage;
    }

    public BrokerException(BasicRet basicRet) {
        super(Strings.nullToEmpty(basicRet.getMsg()));
        this.code = basicRet.getCode();
        this.messageArgs = basicRet.getArgsList().toArray();
        this.showMe = basicRet.getShowMe();
    }

    public int getCode() {
        return code;
    }

    public int code() {
        return this.code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String message() {
        return errorMessage;
    }

    public Object[] messageArgs() {
        return this.messageArgs;
    }

    public List<String> getMessageArgs() {
        if (this.messageArgs == null || this.messageArgs.length == 0) {
            return Lists.newArrayList();
        } else {
            return Stream.of(messageArgs).map(Object::toString).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public String getShowMe() {
        return showMe;
    }

    public String showMe() {
        return showMe;
    }

    @Override
    public String getMessage() {
        return "Exception:{"
                + "code:" + code
                + ", detailMessage:" + Strings.nullToEmpty(super.getMessage())
                + ", messageArgs:" + JsonUtil.defaultGson().toJson(getMessageArgs())
                + ", showMe:" + Strings.nullToEmpty(getShowMe())
                + "}";
    }
}
