package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtils {

	private static final ResourceLoader LOADER = ApplicationResourceLoader.get();

	public static String loadAsText(String location) {
		try {
			return LOADER.getResource(location)
				.getContentAsString(StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
