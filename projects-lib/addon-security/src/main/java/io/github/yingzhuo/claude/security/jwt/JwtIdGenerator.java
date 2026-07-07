package io.github.yingzhuo.claude.security.jwt;

import java.util.function.Supplier;

@FunctionalInterface
public interface JwtIdGenerator extends Supplier<String> {
}
