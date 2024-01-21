package io.bhex.broker.core.domain;

import io.bhex.broker.common.exception.BrokerErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResult<T> {

    public static final int SUCCESS_CODE = 0;

    private boolean success;
    private int code;
    private String msg;
    private T data;
    @Builder.Default
    private Object extendInfo = ""; // 扩展信息
    @Builder.Default
    private String showMe = ""; // this message will be followed tips to user.

    public static <T> BaseResult<T> success() {
        return BaseResult.<T>builder().success(true).code(SUCCESS_CODE).build();
    }

    public static <T> BaseResult<T> success(T data) {
        return BaseResult.<T>builder().success(true).code(SUCCESS_CODE).data(data).build();
    }

    public static <T> BaseResult<T> success(int code, String msg, T data) {
        return BaseResult.<T>builder().success(true).code(code).msg(msg).data(data).build();
    }

    public static <T> BaseResult<T> fail(int code, String msg) {
        return BaseResult.<T>builder().success(false).code(code).msg(msg).build();
    }

    public static <T> BaseResult<T> fail(int code, String msg, Object extendInfo) {
        return BaseResult.<T>builder().success(false).code(code).msg(msg).extendInfo(extendInfo).build();
    }

    public static <T> BaseResult<T> fail(int code, String msg, Object extendInfo, String showMe) {
        return BaseResult.<T>builder().success(false).code(code).msg(msg).extendInfo(extendInfo).showMe(showMe).build();
    }

    public static <T> BaseResult<T> fail(BrokerErrorCode errorCode) {
        return BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).build();
    }

    public static <T> BaseResult<T> fail(BrokerErrorCode errorCode, Object extendInfo) {
        return BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).extendInfo(extendInfo).build();
    }

    public static <T> BaseResult<T> fail(BrokerErrorCode errorCode, Object extendInfo, String showMe) {
        return BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).extendInfo(extendInfo).showMe(showMe).build();
    }

}

