package io.github.yingzhuo.claude.utility;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.util.Map;

public class KeyStoreResourceProtocolResolver extends AbstractSecurityResourceProtocolResolver {

	private static final String PREFIX = "keystore:";
	private static final String PARAM_STOREPASS = "storepass";
	private static final String PARAM_TYPE = "type";

	@Override
	protected String getPrefix() {
		return PREFIX;
	}

	@Override
	protected Resource createResource(Resource delegatingResource, Map<String, String> queryParams) {
		var pass = queryParams.get(PARAM_STOREPASS);
		if (!StringUtils.hasText(pass)) {
			throw new IllegalArgumentException("KeyStore resource requires pass parameter");
		}

		var typeName = queryParams.getOrDefault(PARAM_TYPE, "");
		var keyStoreType = parseKeyStoreType(typeName);
		return new KeyStoreResource(delegatingResource, pass, keyStoreType);
	}

	private KeyStoreResource.KeyStoreType parseKeyStoreType(@Nullable String typeName) {
		if (!StringUtils.hasText(typeName)) {
			return KeyStoreResource.KeyStoreType.PKCS12;
		}
		try {
			return KeyStoreResource.KeyStoreType.valueOf(typeName.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Can't parse keystore type: '" + typeName + "'");
		}
	}
}
