package io.github.yingzhuo.claude.utility;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractSecurityResource extends AbstractResource {

	protected final Resource delegatingResource;

	protected AbstractSecurityResource(Resource delegatingResource) {
		this.delegatingResource = delegatingResource;
	}

	@Override
	public final String getDescription() {
		return this.toString();
	}

	@Override
	public final InputStream getInputStream() throws IOException {
		return delegatingResource.getInputStream();
	}

}
