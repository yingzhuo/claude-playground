package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @see RSA256AlgorithmProvider
 */
@FunctionalInterface
public interface AlgorithmProvider extends Supplier<Algorithm> {
}
