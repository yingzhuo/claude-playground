package io.github.yingzhuo.claude.core;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class ApplicationBootJackson3 implements JsonMapperBuilderCustomizer {

    @Override
    public void customize(JsonMapper.Builder builder) {
        builder.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

}
