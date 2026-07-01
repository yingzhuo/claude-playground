package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.util.Assert;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unchecked")
public final class PemUtils {

	public static <T extends X509Certificate> T getCertificate(String pemLocation) {
		var content = PemContent.of(ResourceUtils.loadAsText(pemLocation));
		var certList = content.getCertificates();
		Assert.notEmpty(certList, "Certificate list is empty");
		return (T) certList.getFirst();
	}

	public static <T extends PublicKey> T getPublicKeyFromCertificate(String pemLocation) {
		return (T) getCertificate(pemLocation).getPublicKey();
	}

	public static <T extends PrivateKey> T getPrivateKey(String pemLocation, @Nullable String password) {
		var content = PemContent.of(ResourceUtils.loadAsText(pemLocation));
		var privateKey = content.getPrivateKey(password);
		Assert.notNull(privateKey, "Private key is null");
		return (T) privateKey;
	}

}
