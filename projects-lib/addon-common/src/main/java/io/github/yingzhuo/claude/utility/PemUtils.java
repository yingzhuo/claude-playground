package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PemUtils {

	private static final ResourceLoader RESOURCE_LOADER = ApplicationResourceLoader.get();

	@SuppressWarnings("unchecked")
	public static <T extends X509Certificate> T getCertificate(String resourceLocation) {
		try (var inputStream = RESOURCE_LOADER.getResource(resourceLocation).getInputStream()) {
			var content = PemContent.load(inputStream);
			return (T) content.getCertificates().get(0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends PublicKey> T getPublicKeyFromCertificate(String resourceLocation) {
		return (T) getCertificate(resourceLocation).getPublicKey();
	}

	@SuppressWarnings("unchecked")
	public static <T extends PrivateKey> T getPrivateKey(String resourceLocation, @Nullable String password) {
		try (var inputStream = RESOURCE_LOADER.getResource(resourceLocation).getInputStream()) {
			var content = PemContent.load(inputStream);
			var privateKey = content.getPrivateKey(password);
			Assert.notNull(privateKey, "Private key is null");
			return (T) privateKey;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
