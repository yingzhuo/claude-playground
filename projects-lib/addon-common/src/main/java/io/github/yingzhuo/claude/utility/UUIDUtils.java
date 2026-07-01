package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UUIDUtils {

	public static String randomUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String randomUUID36() {
		return UUID.randomUUID().toString();
	}

}
