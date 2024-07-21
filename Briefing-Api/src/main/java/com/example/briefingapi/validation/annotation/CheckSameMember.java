package com.example.briefingapi.validation.annotation;

import java.lang.annotation.*;

import com.example.briefingapi.validation.validator.CheckSameMemberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;



@Documented
@Constraint(validatedBy = CheckSameMemberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSameMember {
    String message() default "로그인 한 사용자와 대상 사용자가 동일하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
