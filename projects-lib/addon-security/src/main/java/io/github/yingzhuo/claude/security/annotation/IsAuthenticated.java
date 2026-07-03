package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated()")
public @interface IsAuthenticated {
}
