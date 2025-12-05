package com.phuocpt98.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        minLength = constraintAnnotation.min();
        maxLength = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= minLength && s.length() <= maxLength;
    }


}
