package io.github.yingzhuo.claude.utility;

import org.springframework.core.io.Resource;

import java.util.Map;

public class PEMResourceProtocolResolver extends AbstractSecurityResourceProtocolResolver {

	private static final String PARAM_KEYPASS = "keypass";

	@Override
	protected String getPrefix() {
		return "pem:";
	}

	@Override
	protected Resource createResource(Resource delegatingResource, Map<String, String> queryParams) {
		var password = queryParams.get(PARAM_KEYPASS);
		return new PEMResource(delegatingResource, password);
	}

}
