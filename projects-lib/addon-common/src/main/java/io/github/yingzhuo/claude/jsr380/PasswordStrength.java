package io.github.yingzhuo.claude.jsr380;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
public @interface PasswordStrength {

	String message() default "密码必须包含字母、数字和特殊字符";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
