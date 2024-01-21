package io.bhex.broker.core.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Valid;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HbtcValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Valid
public @interface ValidHbtcParam {

    boolean skipNullAndEmpty() default false;

    String message() default "30002";

    HbtcParamType type();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default ".*";
}
