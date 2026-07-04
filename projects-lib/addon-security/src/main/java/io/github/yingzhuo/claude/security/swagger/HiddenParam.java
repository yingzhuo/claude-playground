package io.github.yingzhuo.claude.security.swagger;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Inherited
@Documented
@Target({PARAMETER, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true)
public @interface HiddenParam {
}
