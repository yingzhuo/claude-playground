package io.github.yingzhuo.claude.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordEncoderFactories {

	@SuppressWarnings("deprecation")
	public static PasswordEncoder createDefaults() {
		final var encodingId = "bcrypt";
		final var encoders = new HashMap<String, PasswordEncoder>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());

		var encoder = new DelegatingPasswordEncoder(encodingId, encoders);
		encoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
		return encoder;
	}

}
