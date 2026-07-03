package io.github.yingzhuo.claude.utility;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class KeyStoreResource extends AbstractSecurityResource {

	@Getter
	private final String storePass;

	@Getter
	private final KeyStoreType keyStoreType;

	@Getter
	private final KeyStore keyStore;

	public KeyStoreResource(Resource delegatingResource, String storePass) {
		this(delegatingResource, storePass, KeyStoreType.PKCS12);
	}

	public KeyStoreResource(Resource delegatingResource, String storePass, KeyStoreType keyStoreType) {
		super(delegatingResource);
		this.storePass = storePass;
		this.keyStoreType = keyStoreType;

		try (var in = delegatingResource.getInputStream()) {
			this.keyStore = KeyStore.getInstance(keyStoreType.name());
			this.keyStore.load(in, storePass.toCharArray());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends PrivateKey> T getPrivateKey(String alias, @Nullable String keypass) {
		try {
			return (T) keyStore.getKey(alias, Optional.ofNullable(keypass).orElse("").toCharArray());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends X509Certificate> T getCertificate(String alias) {
		try {
			return (T) keyStore.getCertificate(alias);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends PublicKey> T getPublicKey(String alias) {
		return (T) getCertificate(alias).getPublicKey();
	}

	public KeyPair getKeyPair(String alias, @Nullable String keypass) {
		return new KeyPair(getCertificate(alias).getPublicKey(), getPrivateKey(alias, keypass));
	}

	public List<String> getAliases() {
		List<String> aliases = new ArrayList<>();
		try {
			var enumeration = keyStore.aliases();
			while (enumeration.hasMoreElements()) {
				aliases.add(enumeration.nextElement());
			}
		} catch (Exception e) {
			throw new RuntimeException("获取别名列表失败: " + e.getMessage(), e);
		}
		return Collections.unmodifiableList(aliases);
	}

	// ----

	public enum KeyStoreType {
		PKCS12, JKS
	}

}
