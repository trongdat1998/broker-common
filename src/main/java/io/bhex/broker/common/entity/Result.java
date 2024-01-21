/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.entity
 *@Date 2018/10/30
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.entity;

import javax.annotation.Nullable;

public final class Result<T> {

    private final int ret;
    private final String msg;
    private final T data;

    private Result(int ret, @Nullable String msg, @Nullable T data) {
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

    public static <Response> Result<Response> of(int ret, String msg, Response data) {
        return new Result<>(ret, msg, data);
    }

    public static <Response> Result<Response> of(int ret, String msg) {
        return new Result<>(ret, msg, null);
    }

}
