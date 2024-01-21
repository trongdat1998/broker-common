package io.bhex.broker.common.grpc.client.annotation;

import java.lang.annotation.*;

/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.grpc.annotation
 *@Date 2018/7/13
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GrpcLog {

    boolean printNoResponse() default false;

}
