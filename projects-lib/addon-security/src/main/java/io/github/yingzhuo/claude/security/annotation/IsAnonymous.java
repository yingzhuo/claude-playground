package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * 允许匿名用户访问
 *
 * @author 应卓
 */
@Inherited
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAnonymous()")
public @interface IsAnonymous {
}
