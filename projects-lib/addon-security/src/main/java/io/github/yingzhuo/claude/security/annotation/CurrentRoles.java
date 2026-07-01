package io.github.yingzhuo.claude.security.annotation;

import io.github.yingzhuo.claude.security.jwt.JwtInfo;
import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CurrentSecurityContext(expression = "authentication.roles")
public @interface CurrentRoles {
}
