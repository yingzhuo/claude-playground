package io.github.yingzhuo.claude.core.cfgbean;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class JsonMapperCustomizer implements JsonMapperBuilderCustomizer {

	private final Environment environment;

	@Override
	public void customize(JsonMapper.Builder builder) {
		// 写
		if (environment.acceptsProfiles(Profiles.of("dev"))) {
			builder.configure(SerializationFeature.INDENT_OUTPUT, true);
		}

		// 读
	}

}
