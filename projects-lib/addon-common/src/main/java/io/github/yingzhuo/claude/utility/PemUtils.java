/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import static org.springframework.util.ClassUtils.getDefaultClassLoader;

/**
 * @author 应卓
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PemUtils {

	private static final ResourceLoader RESOURCE_LOADER = ApplicationResourceLoader.get(getDefaultClassLoader());

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

	public static <T extends PrivateKey> T getPrivateKey(String resourceLocation) {
		return getPrivateKey(resourceLocation, null);
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
