package io.bhex.broker.core.validate;

import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HbtcValidator implements ConstraintValidator<ValidHbtcParam, Object> {

    private HbtcParamType hbtcParamType;

    private boolean skipNullAndEmpty;

    @Override
    public void initialize(ValidHbtcParam constraintAnnotation) {
        hbtcParamType = constraintAnnotation.type();
        skipNullAndEmpty = constraintAnnotation.skipNullAndEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (skipNullAndEmpty && (value == null || Strings.isNullOrEmpty(value.toString()))) {
            return true;
        }
        return hbtcParamType.validate(value);
    }

}
