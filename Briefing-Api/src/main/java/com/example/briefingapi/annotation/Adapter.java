package com.example.briefingapi.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Adapter {
    // 스프링 빈의 이름을 지정
    @AliasFor(annotation = Component.class)
    String value() default "";
}