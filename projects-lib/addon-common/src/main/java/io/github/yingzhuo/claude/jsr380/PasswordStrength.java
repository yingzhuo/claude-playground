/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.jsr380;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
