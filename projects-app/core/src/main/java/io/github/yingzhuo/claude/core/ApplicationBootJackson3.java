package io.github.yingzhuo.claude.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.AnnotatedField;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.introspect.AnnotatedMethod;
import tools.jackson.databind.introspect.JacksonAnnotationIntrospector;
import tools.jackson.databind.json.JsonMapper;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationBootJackson3 implements JsonMapperBuilderCustomizer {

	private final Environment environment;

	@Override
	public void customize(JsonMapper.Builder builder) {
		if (environment.acceptsProfiles(Profiles.of("dev"))) {
			builder.configure(SerializationFeature.INDENT_OUTPUT, true);
		}

		// 全局敏感字段忽略
		builder.annotationIntrospector(new SensitiveFieldIntrospector());
	}

	// -----------------------------------------------------------------------------------------------------

	private static class SensitiveFieldIntrospector extends JacksonAnnotationIntrospector {

		private static final Set<String> SENSITIVE = Set.of("password", "secret");

		@Override
		public boolean hasIgnoreMarker(MapperConfig<?> config, AnnotatedMember member) {
			if (SENSITIVE.contains(resolvePropertyName(member))) {
				return true;
			}
			return super.hasIgnoreMarker(config, member);
		}

		private String resolvePropertyName(AnnotatedMember member) {
			if (member instanceof AnnotatedField field) {
				return field.getName();
			}
			if (member instanceof AnnotatedMethod method) {
				var name = method.getName();
				if (name.startsWith("get") && name.length() > 3) {
					return Character.toLowerCase(name.charAt(3)) + name.substring(4);
				}
				if (name.startsWith("set") && name.length() > 3) {
					return Character.toLowerCase(name.charAt(3)) + name.substring(4);
				}
				if (name.startsWith("is") && name.length() > 2) {
					return Character.toLowerCase(name.charAt(2)) + name.substring(3);
				}
			}
			return member.getName();
		}
	}
}
