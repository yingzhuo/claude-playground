package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.utility.PemUtils;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

@FunctionalInterface
public interface AlgorithmProvider extends Supplier<Algorithm> {

	@Builder
	class RSA256 implements AlgorithmProvider {

		private String pemLocation;
		private @Nullable String keyPassword;

		@Override
		public Algorithm get() {
			return Algorithm.RSA256(
				PemUtils.getPublicKeyFromCertificate(pemLocation),
				PemUtils.getPrivateKey(pemLocation, keyPassword)
			);
		}
	}
}
