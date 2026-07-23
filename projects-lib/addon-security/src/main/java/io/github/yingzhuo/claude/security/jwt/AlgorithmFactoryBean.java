package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.security.KeyStore;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

@Builder
public class AlgorithmFactoryBean implements FactoryBean<Algorithm>, ResourceLoaderAware, InitializingBean {

    private @Setter ResourceLoader resourceLoader;
    private @Nullable String storeType;
    private String storeLocation;
    private String storepass;
    private String alias;
    private @Nullable String keypass;
    private @Nullable AlgKind algKind;

    @Override
    public @Nullable Algorithm getObject() throws Exception {
        Assert.hasText(storeLocation, "storeLocation is required");
        Assert.hasText(storepass, "storepass is required");
        Assert.hasText(alias, "alias is required");


        try (var inputStream = resourceLoader.getResource(storeLocation).getInputStream()) {
            var store = KeyStore.getInstance(this.storeType);
            store.load(inputStream, storepass.toCharArray());

            var certificate = store.getCertificateChain(alias)[0];
            var publicKey = (ECPublicKey) certificate.getPublicKey();
            var privateKey = (ECPrivateKey) store.getKey(alias, keypass.toCharArray());

            return switch (algKind) {
                case ECDSA384 -> Algorithm.ECDSA384(publicKey, privateKey);
                default -> throw new UnsupportedOperationException("'" + algKind.name() + "' not supported yet.");
            };
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (keypass == null) {
            keypass = "";
        }

        if (storeType == null) {
            storeType = "PKCS12";
        }

        if (algKind == null) {
            algKind = AlgKind.ECDSA384;
        }
    }

    @Override
    public Class<?> getObjectType() {
        return Algorithm.class;
    }

    public enum AlgKind {
        ECDSA384
    }

}
