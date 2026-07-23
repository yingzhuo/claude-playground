package io.github.yingzhuo.claude.core.m.admin.vo;

import io.github.yingzhuo.claude.model.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Deprecated(forRemoval = true)
@Schema(description = "用户列表项")
public class UserListItemVO {

    @Schema(description = "用户ID")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Nullable
    @Schema(description = "出生日期")
    private LocalDate dob;

    @Nullable
    @Schema(description = "电子邮件地址")
    private String email;

    @Nullable
    @Schema(description = "头像地址")
    private String avatarUrl;

    @Schema(description = "性别（MALE / FEMALE / UNKNOWN）")
    private Gender gender;

    @Schema(description = "启用状态")
    private boolean enabled;

    @Schema(description = "记录创建时间")
    private LocalDateTime createdAt;

    @Nullable
    @Schema(description = "注销时间（为空表示未注销）")
    private LocalDateTime cancelledAt;

}
