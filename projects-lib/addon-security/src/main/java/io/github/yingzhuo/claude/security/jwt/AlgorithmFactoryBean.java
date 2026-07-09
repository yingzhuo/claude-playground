package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.security.KeyStore;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

@Builder
public class AlgorithmFactoryBean implements FactoryBean<Algorithm>, ResourceLoaderAware {

	private @Setter ResourceLoader resourceLoader;
	private String storeLocation;
	private String storepass;
	private String alias;
	private @Nullable String keypass = "";

	@Override
	public @Nullable Algorithm getObject() throws Exception {
		Assert.hasText(storeLocation, "storeLocation is required");
		Assert.hasText(storepass, "storepass is required");
		Assert.hasText(alias, "alias is required");

		if (keypass == null) {
			keypass = "";
		}

		try (var inputStream = resourceLoader.getResource(storeLocation).getInputStream()) {
			var store = KeyStore.getInstance("PKCS12");
			store.load(inputStream, storepass.toCharArray());

			var certificate = store.getCertificateChain(alias)[0];
			var publicKey = (ECPublicKey) certificate.getPublicKey();
			var privateKey = (ECPrivateKey) store.getKey(alias, keypass.toCharArray());
			return Algorithm.ECDSA384(publicKey, privateKey);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Algorithm.class;
	}
}
