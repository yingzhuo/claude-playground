package io.github.yingzhuo.claude.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordEncoderFactories {

	public static PasswordEncoder createDefault() {
		return new BCryptPasswordEncoder();
	}

}
