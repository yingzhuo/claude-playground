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

package io.github.yingzhuo.claude.spring;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtils {

	public static final ResourceLoader RESOURCE_LOADER = ApplicationResourceLoader.get(ClassUtils.getDefaultClassLoader());

	public static ResourceLoader getResourceLoader() {
		return RESOURCE_LOADER;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Resource> T loadResource(String location) {
		Assert.notNull(location, "resource location must not be null");
		return (T) RESOURCE_LOADER.getResource(location);
	}

	public static InputStream loadResourceAsStream(String location) {
		try {
			return loadResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String loadResourceAsText(String location) {
		return loadResourceAsText(location, UTF_8);
	}

	public static String loadResourceAsText(String location, @Nullable Charset charset) {
		Assert.notNull(location, "resource location must not be null");
		charset = Objects.requireNonNullElse(charset, UTF_8);
		try {
			return loadResource(location).getContentAsString(charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static byte[] loadResourceAsBytes(String location) {
		Assert.notNull(location, "resource location must not be null");
		try {
			return loadResource(location).getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Properties loadResourceAsProperties(String location, boolean xmlFormat) {
		Assert.notNull(location, "resource location must not be null");
		try (var inputStream = loadResourceAsStream(location)) {
			var properties = new Properties();
			if (xmlFormat) {
				properties.loadFromXML(inputStream);
			} else {
				properties.load(inputStream);
			}
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
