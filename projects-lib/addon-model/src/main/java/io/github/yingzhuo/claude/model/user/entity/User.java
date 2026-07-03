package io.github.yingzhuo.claude.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 */
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
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;

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
