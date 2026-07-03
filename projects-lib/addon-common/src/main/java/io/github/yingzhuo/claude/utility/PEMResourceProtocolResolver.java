package io.github.yingzhuo.claude.utility;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.LinkedHashMap;
import java.util.Map;

class PEMResourceProtocolResolver implements ProtocolResolver {

	private static final String PREFIX = "pem:";

	@Override
	public @Nullable Resource resolve(String location, ResourceLoader resourceLoader) {
		if (!location.startsWith(PREFIX)) {
			return null;
		}

		var rest = location.substring(PREFIX.length());
		var queryParams = parseQueryParams(rest);
		var resourceLocation = rest.contains("?") ? rest.substring(0, rest.indexOf('?')) : rest;

		var password = queryParams.get("password");
		var delegatingResource = resourceLoader.getResource(resourceLocation);
		return new PEMResource(delegatingResource, password);
	}

	private Map<String, String> parseQueryParams(String location) {
		var queryIndex = location.indexOf('?');
		if (queryIndex < 0) {
			return Map.of();
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
