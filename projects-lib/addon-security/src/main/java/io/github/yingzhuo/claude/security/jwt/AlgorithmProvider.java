package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.utility.KeyStoreResource;
import lombok.Builder;

import java.util.function.Supplier;

@FunctionalInterface
public interface AlgorithmProvider extends Supplier<Algorithm> {

	@Builder
	class RSA256 implements AlgorithmProvider {

		private KeyStoreResource keyStoreResource;
		private String alias;
		private String keyPassword;

		@Override
		public Algorithm get() {
			return Algorithm.RSA256(keyStoreResource.getPublicKey(alias), keyStoreResource.getPrivateKey(alias, keyPassword));
		}
	}

}
