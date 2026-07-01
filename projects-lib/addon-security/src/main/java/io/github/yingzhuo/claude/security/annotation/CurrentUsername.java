package io.github.yingzhuo.claude.security.annotation;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CurrentSecurityContext(expression = "authentication.username")
public @interface CurrentUsername {
}
