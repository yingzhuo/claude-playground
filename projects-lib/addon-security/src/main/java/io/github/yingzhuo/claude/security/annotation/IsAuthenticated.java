package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * 要求用户已登录（认证通过）
 *
 * @author 应卓
 */
@Inherited
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated()")
public @interface IsAuthenticated {
}
