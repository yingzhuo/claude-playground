package io.github.yingzhuo.claude.misc;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${spring.application.name}")
public @interface ApplicationName {
}
