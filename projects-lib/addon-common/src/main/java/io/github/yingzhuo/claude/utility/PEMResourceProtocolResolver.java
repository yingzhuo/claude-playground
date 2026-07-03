package io.github.yingzhuo.claude.utility;

import org.springframework.core.io.Resource;

import java.util.Map;

public class PEMResourceProtocolResolver extends AbstractSecurityResourceProtocolResolver {

	@Override
	protected String getPrefix() {
		return "pem:";
	}

	@Override
	protected Resource createResource(Resource delegatingResource, Map<String, String> queryParams) {
		var password = queryParams.get("password");
		return new PEMResource(delegatingResource, password);
	}
}
