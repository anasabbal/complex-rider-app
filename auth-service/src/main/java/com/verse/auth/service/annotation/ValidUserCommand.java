package com.verse.auth.service.annotation;

import com.verse.auth.service.validator.UserCommandValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserCommandValidator.class)
public @interface ValidUserCommand {

    String message() default "Invalid user data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
