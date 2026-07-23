package io.github.yingzhuo.claude.model.admin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_admin")
@Schema(description = "管理员实体")
@JsonIgnoreProperties("password")
public class Admin implements Serializable {

    /**
     * 数据库ID
     */
    @Schema(description = "数据库ID")
    @TableId
    private String id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", maxLength = 20)
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码", maxLength = 100)
    private String password;

    /**
     * 角色
     */
    @Schema(description = "角色（NORMAL / SUPER）")
    private AdminRole role;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @Nullable
    private LocalDateTime lastLoginTime;
}
