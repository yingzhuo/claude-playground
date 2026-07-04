package io.github.yingzhuo.claude.security.annotation;

import io.github.yingzhuo.claude.misc.SwaggerConstants;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Inherited
@Documented
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = SwaggerConstants.AUTH_HEADER)
public @interface SecurityRequirement {
}
