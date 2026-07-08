package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.github.yingzhuo.claude.model.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "修改个人信息请求")
public class UpdateProfileRequestDTO {

	@Nullable
	@Size(max = 10, message = "昵称长度不能超过 {max} 位")
	@Schema(description = "昵称（不传则不修改）", maxLength = 10)
	private String nickname;

	@Nullable
	@Schema(description = "性别（不传则不修改）")
	private Gender gender;

	@Nullable
	@PastOrPresent(message = "出生日期不可晚于今天")
	@Schema(description = "出生日期（不传则不修改）")
	private LocalDate dob;

	@Nullable
	@Email(message = "电子邮件地址格式不正确")
	@Size(max = 50, message = "电子邮件地址长度不能超过 {max} 位")
	@Schema(description = "电子邮件地址（不传则不修改）", maxLength = 50)
	private String email;

	@Nullable
	@URL(message = "头像地址格式不正确")
	@Size(max = 300, message = "头像地址长度不能超过 {max} 位")
	@Schema(description = "头像地址（不传则不修改）", maxLength = 300)
	private String avatarUrl;
}
