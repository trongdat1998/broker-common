package io.bhex.broker.core.domain;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OAuthCheck {

    /**
     * request require which oauth permission
     */
    String requiredPermission();

}
