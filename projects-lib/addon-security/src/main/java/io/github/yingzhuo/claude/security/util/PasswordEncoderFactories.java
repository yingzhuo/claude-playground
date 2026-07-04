package io.github.yingzhuo.claude.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@SuppressWarnings("deprecation")
public final class PasswordEncoderFactories {

	private static final Map<String, PasswordEncoder> ENCODERS;

	static {
		ENCODERS = Map.of(
			"bcrypt", new BCryptPasswordEncoder(),
			"noop", NoOpPasswordEncoder.getInstance()
		);
	}

	public static PasswordEncoder createDefault() {
		var encoder = new DelegatingPasswordEncoder("bcrypt", ENCODERS);
		encoder.setDefaultPasswordEncoderForMatches(ENCODERS.get("noop"));
		return encoder;
	}

}
