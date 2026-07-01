package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.function.Supplier;

/**
 * @see RSA256AlgorithmProvider
 */
@FunctionalInterface
public interface AlgorithmProvider extends Supplier<Algorithm> {
}
