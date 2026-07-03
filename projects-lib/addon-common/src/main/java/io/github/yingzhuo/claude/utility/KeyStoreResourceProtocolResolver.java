package io.github.yingzhuo.claude.utility;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.util.Map;

public class KeyStoreResourceProtocolResolver extends AbstractSecurityResourceProtocolResolver {

    @Override
    protected String getPrefix() {
        return "keystore:";
    }

    @Override
    protected Resource createResource(Resource delegatingResource, Map<String, String> queryParams) {
        var pass = queryParams.get("pass");
        if (!StringUtils.hasText(pass)) {
            throw new IllegalArgumentException("KeyStore resource requires pass parameter");
        }

        var typeName = queryParams.getOrDefault("type", "");
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
            throw new RuntimeException("Can't parse keystore type: " + typeName);
        }
    }
}
