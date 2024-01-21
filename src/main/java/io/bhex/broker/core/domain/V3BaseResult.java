package io.bhex.broker.core.domain;

import com.google.common.collect.Lists;
import io.bhex.broker.common.exception.BrokerErrorCode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class V3BaseResult<T> {

    public static final int SUCCESS_CODE = 0;

    private boolean success;
    private int code;
    private String msg;
    private T data;
    @Builder.Default
    private List<String> extendInfos = Lists.newArrayList(); // extends info for format message
    @Builder.Default
    private String showMe = ""; // this message will be followed tips to user.

    public static <T> V3BaseResult<T> success() {
        return V3BaseResult.<T>builder().success(true).code(SUCCESS_CODE).build();
    }

    public static <T> V3BaseResult<T> success(T data) {
        return V3BaseResult.<T>builder().success(true).code(SUCCESS_CODE).data(data).build();
    }

    public static <T> V3BaseResult<T> success(int code, String msg, T data) {
        return V3BaseResult.<T>builder().success(true).code(code).msg(msg).data(data).build();
    }

    public static <T> V3BaseResult<T> fail(int code, String msg) {
        return V3BaseResult.<T>builder().success(false).code(code).msg(msg).build();
    }

    public static <T> V3BaseResult<T> fail(BrokerErrorCode errorCode) {
        return V3BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).build();
    }

    public static <T> V3BaseResult<T> fail(int code, String msg, List<String> extendInfos) {
        return V3BaseResult.<T>builder().success(false).code(code).msg(msg).extendInfos(extendInfos).build();
    }

    public static <T> V3BaseResult<T> fail(BrokerErrorCode errorCode, List<String> extendInfos) {
        return V3BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).extendInfos(extendInfos).build();
    }

    public static <T> V3BaseResult<T> fail(int code, String msg, String showMe) {
        return V3BaseResult.<T>builder().success(false).code(code).msg(msg).showMe(showMe).build();
    }

    public static <T> V3BaseResult<T> fail(BrokerErrorCode errorCode, String showMe) {
        return V3BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).showMe(showMe).build();
    }

    public static <T> V3BaseResult<T> fail(int code, String msg, List<String> extendInfos, String showMe) {
        return V3BaseResult.<T>builder().success(false).code(code).msg(msg).extendInfos(extendInfos).showMe(showMe).build();
    }

    public static <T> V3BaseResult<T> fail(BrokerErrorCode errorCode, List<String> extendInfos, String showMe) {
        return V3BaseResult.<T>builder().success(false).code(errorCode.code()).msg(errorCode.msg()).extendInfos(extendInfos).showMe(showMe).build();
    }

    public static <T> V3BaseResult<T> fail(V3BaseResult<?> other) {
        return V3BaseResult.<T>builder().success(false).code(other.getCode()).msg(other.getMsg()).extendInfos(other.getExtendInfos()).showMe(other.getShowMe()).build();
    }

}

