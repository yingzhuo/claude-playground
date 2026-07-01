package io.github.yingzhuo.claude.jsr380;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 密码强度校验注解。
 * <p>
 * 校验字符串是否符合密码强度要求：
 * <ul>
 *   <li>长度 8-32 位</li>
 *   <li>必须包含至少一个字母</li>
 *   <li>必须包含至少一个数字</li>
 *   <li>必须包含至少一个特殊字符</li>
 * </ul>
 * </p>
 *
 * @author 应卓
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
public @interface PasswordStrength {

	String message() default "密码必须包含字母、数字和特殊字符";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
