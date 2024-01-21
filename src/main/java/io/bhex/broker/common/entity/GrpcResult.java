/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.domain
 *@Date 2018/6/27
 *@Author peiwei.ren@bhex.io
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

import javax.annotation.Nullable;

public final class GrpcResult<T> {

    public static final Integer SUCCESS_RET = 0;
    public static final Integer BH_SUCCESS_RET = 200;

    private final int ret;
    private final String msg;
    private final T data;

    private GrpcResult(int ret, @Nullable String msg, @Nullable T data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }

    public int ret() {
        return ret;
    }

    public String msg() {
        return msg;
    }

    public T data() {
        return data;
    }

    public int getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <Response> GrpcResult<Response> of(int ret, String msg, Response data) {
        return new GrpcResult<>(ret, msg, data);
    }

    public static <Response> GrpcResult<Response> of(int ret, String msg) {
        return new GrpcResult<>(ret, msg, null);
    }

    public static <Response> GrpcResult<Response> of(int ret, Response data) {
        return new GrpcResult<>(ret, null, null);
    }

    public static <Response> GrpcResult<Response> of(int ret) {
        return new GrpcResult<>(ret, null, null);
    }

    public static <Response> GrpcResult<Response> success(Response response) {
        return new GrpcResult<>(SUCCESS_RET, null, response);
    }

    public boolean isSuccess() {
        return this.ret == SUCCESS_RET || this.ret == BH_SUCCESS_RET;
    }

}
