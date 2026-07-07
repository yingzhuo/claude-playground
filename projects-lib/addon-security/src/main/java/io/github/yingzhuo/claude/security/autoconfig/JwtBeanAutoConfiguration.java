package io.github.yingzhuo.claude.security.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.security.util.PasswordEncoderFactories;
import io.github.yingzhuo.claude.utility.UUIDUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyStore;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

@AutoConfiguration
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDefault();
	}

	@Bean
	@ConditionalOnMissingBean
	public Algorithm jwtAlgorithm(ResourceLoader resourceLoader) throws Exception {
		var storepass = "123456".toCharArray();
		var keypass = "123456".toCharArray();
		var alias = "ecdsa384";

		try (var inputStream = resourceLoader.getResource("classpath:META-INF/claude-playground.pfx").getInputStream()) {
			var store = KeyStore.getInstance("PKCS12");
			store.load(inputStream, storepass);

			var certificate = store.getCertificateChain(alias)[0];
			var publicKey = (ECPublicKey) certificate.getPublicKey();
			var privateKey = (ECPrivateKey) store.getKey(alias, keypass);
			return Algorithm.ECDSA384(publicKey, privateKey);
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtIdGenerator jwtIdGenerator() {
		return UUIDUtils::randomUUIDv7;
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtCreator jwtCreator(Algorithm alg, JwtIdGenerator idGen) {
		return new SimpleJwtCreator(alg, idGen, 4);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtVerifier jwtVerifier(Algorithm alg) {
		return new SimpleJwtVerifier(alg);
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenResolver tokenResolver() {
		return TokenResolver.getDefault();
	}

}
