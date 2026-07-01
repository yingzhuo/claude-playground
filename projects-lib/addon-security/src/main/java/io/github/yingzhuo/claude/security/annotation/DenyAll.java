package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * 拒绝所有用户访问
 *
 * @author 应卓
 */
@Inherited
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("denyAll()")
public @interface DenyAll {
}
