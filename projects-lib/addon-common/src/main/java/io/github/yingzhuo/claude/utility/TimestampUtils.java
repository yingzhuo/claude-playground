package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimestampUtils {

	public static String get() {
		return String.valueOf(System.currentTimeMillis());
	}

}
