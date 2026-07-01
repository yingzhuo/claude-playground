package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.utility.PemUtils;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see AlgorithmProvider
 */
@Builder
public class RSA256AlgorithmProvider implements AlgorithmProvider, InitializingBean {

	private String pemLocation;
	private @Nullable String keyPassword;

	@Override
	public Algorithm get() {
		return Algorithm.RSA256(
			PemUtils.getPublicKeyFromCertificate(pemLocation),
			PemUtils.getPrivateKey(pemLocation, keyPassword)
		);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(pemLocation, "cert pem location is required");
	}

}
