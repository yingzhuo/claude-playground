package io.github.yingzhuo.claude.utility;

import com.fasterxml.uuid.Generators;
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

	/**
	 * 生成 UUIDv7（基于时间戳的有序UUID，适合数据库聚簇索引）
	 *
	 * @return 32位小写十六进制字符串，不含连字符
	 */
	public static String randomUUIDv7() {
		return Generators.timeBasedEpochGenerator().generate().toString().replace("-", "");
	}
}
