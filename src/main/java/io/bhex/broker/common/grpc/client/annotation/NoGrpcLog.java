package io.bhex.broker.common.grpc.client.annotation;

import java.lang.annotation.*;

/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.common.grpc.client.annotation
 *@Date 2019/1/21
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoGrpcLog {
}
