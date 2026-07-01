package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * 要求使用非 Remember-Me 的方式认证（即完全认证）
 *
 * @author 应卓
 */
@Inherited
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isFullyAuthenticated()")
public @interface IsFullyAuthenticated {
}
