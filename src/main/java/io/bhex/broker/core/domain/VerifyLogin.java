package io.bhex.broker.core.domain;

import java.lang.annotation.*;

/**********************************
 *@项目名称: broker-common
 *@文件名称: io.bhex.broker.core.domain
 *@Date 2018/11/14
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyLogin {

    /**
     * if authorize failed,
     * @return True for yes and False will continue process
     */
    boolean interrupt() default true;

}
