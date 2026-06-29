/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author 应卓
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@Schema(description = "用户实体")
public class User implements Serializable {

	/**
	 * 数据库ID
	 */
	@Schema(description = "数据库ID")
	@TableId
	private String id;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 出生日期
	 */
	@Schema(description = "出生日期")
	private LocalDate dob;

	/**
	 * 性别
	 */
	@Schema(description = "性别（MALE / FEMALE / UNKNOWN）")
	private Gender gender;

	/**
	 * 记录创建时间
	 */
	@Schema(description = "记录创建时间")
	private LocalDateTime createdAt;

}
