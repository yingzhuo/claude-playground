package io.github.yingzhuo.claude.utility;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import static org.junit.jupiter.api.Assertions.*;

class PEMResourceProtocolResolverTest {

	private final PEMResourceProtocolResolver resolver = new PEMResourceProtocolResolver();

	private final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

	@Test
	void nonMatchingPrefix_shouldReturnNull() {
		var result = resolver.resolve("classpath:test.pem", resourceLoader);
		assertNull(result);
	}

	@Test
	void matchingPrefix_shouldReturnPEMResource() {
		var result = resolver.resolve("pem:classpath:test.pem", resourceLoader);
		assertInstanceOf(PEMResource.class, result);
	}

	@Test
	void withPasswordParameter_shouldReturnPEMResource() {
		var result = resolver.resolve("pem:classpath:test.pem?keypass=secret", resourceLoader);
		assertInstanceOf(PEMResource.class, result);
	}

	@Test
	void pemResourceShouldContainCertificate() {
		var result = resolver.resolve("pem:classpath:test.pem", resourceLoader);
		var pemResource = (PEMResource) result;
		assertNotNull(pemResource.getCertificate());
	}
}
