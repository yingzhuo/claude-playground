package io.github.yingzhuo.claude.model.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 性别枚举
 *
 */
@Schema(description = "性别枚举")
public enum Gender {

	/**
	 * 男性
	 */
	MALE,

	/**
	 * 女性
	 */
	FEMALE,

	/**
	 * 未知
	 */
	UNKNOWN

}
