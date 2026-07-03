package io.github.yingzhuo.claude.utility;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

class KeyStoreResourceProtocolResolver implements ProtocolResolver {

	private static final String PREFIX = "keystore:";

	@Override
	public @Nullable Resource resolve(String location, ResourceLoader resourceLoader) {
		if (!location.startsWith(PREFIX)) {
			return null;
		}

		var rest = location.substring(PREFIX.length());
		var queryParams = parseQueryParams(rest);
		var resourceLocation = rest.contains("?") ? rest.substring(0, rest.indexOf('?')) : rest;

		var pass = queryParams.get("pass");
		if (!StringUtils.hasText(pass)) {
			throw new IllegalArgumentException("KeyStore resource requires pass parameter: " + location);
		}

		var typeName = queryParams.getOrDefault("type", "");
		var keyStoreType = parseKeyStoreType(typeName);

		var delegatingResource = resourceLoader.getResource(resourceLocation);
		return new KeyStoreResource(delegatingResource, pass, keyStoreType);
	}

	private KeyStoreResource.KeyStoreType parseKeyStoreType(@Nullable String typeName) {
		if (!StringUtils.hasText(typeName)) {
			return KeyStoreResource.KeyStoreType.PKCS12;
		}
		try {
			return KeyStoreResource.KeyStoreType.valueOf(typeName.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Can't parse keystore type: " + typeName);
		}
	}

	private Map<String, String> parseQueryParams(String location) {
		var queryIndex = location.indexOf('?');
		if (queryIndex < 0) {
			return java.util.Map.of();
		}

		var query = location.substring(queryIndex + 1);
		var params = new LinkedHashMap<String, String>();
		for (var param : query.split("&")) {
			var eqIndex = param.indexOf('=');
			if (eqIndex > 0) {
				params.put(param.substring(0, eqIndex), param.substring(eqIndex + 1));
			}
		}
		return Map.copyOf(params);
	}
}
