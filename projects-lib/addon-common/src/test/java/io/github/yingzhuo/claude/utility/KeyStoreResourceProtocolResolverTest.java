package io.github.yingzhuo.claude.utility;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import static org.junit.jupiter.api.Assertions.*;

class KeyStoreResourceProtocolResolverTest {

    private final KeyStoreResourceProtocolResolver resolver = new KeyStoreResourceProtocolResolver();

    private final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    void nonMatchingPrefix_shouldReturnNull() {
        var result = resolver.resolve("classpath:test.p12", resourceLoader);
        assertNull(result);
    }

    @Test
    void matchingPrefixWithPass_shouldReturnKeyStoreResource() {
        var result = resolver.resolve("keystore:classpath:test.p12?pass=changeit", resourceLoader);
        assertInstanceOf(KeyStoreResource.class, result);
    }

    @Test
    void missingPass_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> resolver.resolve("keystore:classpath:test.p12", resourceLoader));
    }

    @Test
    void withTypeParameter_shouldReturnKeyStoreResource() {
        var result = resolver.resolve("keystore:classpath:test.p12?pass=changeit&type=PKCS12", resourceLoader);
        assertInstanceOf(KeyStoreResource.class, result);
    }

    @Test
    void invalidType_shouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> resolver.resolve("keystore:classpath:test.p12?pass=changeit&type=INVALID", resourceLoader));
    }

    @Test
    void keyStoreResourceShouldContainCertificate() {
        var result = resolver.resolve("keystore:classpath:test.p12?pass=changeit", resourceLoader);
        var ksResource = (KeyStoreResource) result;
        assertNotNull(ksResource.getCertificate("test"));
    }
}
