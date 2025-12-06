package com.phuocpt98.demo.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {PasswordValidator.class}
)
public @interface PasswordConstraint {

    String message() default "INVALID_PASSWORD";

    int min() default 6;
    int max() default 60;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
