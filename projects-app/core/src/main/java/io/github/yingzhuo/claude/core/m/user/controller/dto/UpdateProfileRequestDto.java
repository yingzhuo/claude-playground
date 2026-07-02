package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.github.yingzhuo.claude.model.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "修改个人信息请求")
public class UpdateProfileRequestDto {

	@Schema(description = "昵称（不传则不修改）")
	private String nickname;

	@Schema(description = "性别（不传则不修改）")
	private Gender gender;

	@PastOrPresent
	@Schema(description = "出生日期（不传则不修改）")
	private LocalDate dob;
}
