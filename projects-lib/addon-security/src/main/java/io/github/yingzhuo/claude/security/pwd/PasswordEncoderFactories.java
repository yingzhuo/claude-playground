package io.github.yingzhuo.claude.security.pwd;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;

/**
 * @author 应卓
 */
@SuppressWarnings("deprecation")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordEncoderFactories {

	public static PasswordEncoder createDefaults() {
		final var encodingId = "bcrypt";
		final var encoders = new HashMap<String, PasswordEncoder>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("MD4", new Md4PasswordEncoder());
		encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));

		var encoder = new DelegatingPasswordEncoder(encodingId, encoders);
		encoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
		return encoder;
	}

}
