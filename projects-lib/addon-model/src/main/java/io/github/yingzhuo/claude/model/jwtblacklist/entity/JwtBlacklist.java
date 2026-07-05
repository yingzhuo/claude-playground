package io.github.yingzhuo.claude.model.jwtblacklist.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JWT 黑名单实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_jwt_blacklist")
@Schema(description = "JWT黑名单实体")
public class JwtBlacklist implements Serializable {

    /**
     * 数据库ID
     */
    @Schema(description = "数据库ID")
    @TableId
    private String id;

    /**
     * JWT jti 声明值
     */
    @Schema(description = "JWT jti 声明值")
    private String tokenJti;

    /**
     * token 过期时间
     */
    @Schema(description = "token 过期时间，用于清理")
    private LocalDateTime expiredAt;

    /**
     * 拉黑时间
     */
    @Schema(description = "拉黑时间")
    private LocalDateTime createdAt;
}
