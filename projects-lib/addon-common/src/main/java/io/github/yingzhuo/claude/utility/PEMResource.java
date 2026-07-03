package io.github.yingzhuo.claude.utility;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class PEMResource extends AbstractSecurityResource {

	@Nullable
	private final String password;

	private final PemContent pemContent;

	public PEMResource(Resource delegatingResource, @Nullable String password) {
		super(delegatingResource);
		this.password = password;

		try (var in = delegatingResource.getInputStream()) {
			this.pemContent = PemContent.load(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends X509Certificate> T getCertificate() {
		var certs = pemContent.getCertificates();
		if (certs.isEmpty()) {
			throw new NoSuchElementException("No certificate found in PEM resource");
		}
		return (T) certs.getFirst();
	}

	public List<X509Certificate> getCertificateChain() {
		return Collections.unmodifiableList(pemContent.getCertificates());
	}

	@SuppressWarnings("unchecked")
	public <T extends PrivateKey> T getPrivateKey() {
		var key = (password != null) ? pemContent.getPrivateKey(password) : pemContent.getPrivateKey();
		if (key == null) {
			throw new NoSuchElementException("No private key found in PEM resource");
		}
		return (T) key;
	}

	@SuppressWarnings("unchecked")
	public <T extends PrivateKey> T getPrivateKey(@Nullable String keyPassword) {
		var key = pemContent.getPrivateKey(keyPassword);
		if (key == null) {
			throw new NoSuchElementException("No private key found in PEM resource");
		}
		return (T) key;
	}

	@SuppressWarnings("unchecked")
	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	public KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

	public KeyPair getKeyPair(@Nullable String keyPassword) {
		return new KeyPair(getPublicKey(), getPrivateKey(keyPassword));
	}
}
