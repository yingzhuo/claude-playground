package io.github.yingzhuo.claude.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@Schema(description = "用户实体")
@JsonIgnoreProperties({"password", "roles"})
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
     * 电子邮件地址
     */
    @Schema(description = "电子邮件地址", maxLength = 50)
    @Nullable
    private String email;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址", maxLength = 300)
    @Nullable
    private String avatarUrl;

    /**
     * 性别
     */
    @Schema(description = "性别（MALE / FEMALE / UNKNOWN）")
    private Gender gender;

    /**
     * 启用状态（true=启用，false=禁用）
     */
    @Schema(description = "启用状态（true=启用，false=禁用）")
    private boolean enabled;

    /**
     * 记录创建时间
     */
    @Schema(description = "记录创建时间")
    private LocalDateTime createdAt;

    /**
     * 注销时间（为空表示未注销）
     */
    @Schema(description = "注销时间（为空表示未注销）")
    @Nullable
    private LocalDateTime cancelledAt;

    /**
     * 角色列表（非持久化，仅用于 JWT 签发）
     */
    @Schema(description = "角色列表")
    @Nullable
    @TableField(exist = false)
    private List<String> roles;

}
