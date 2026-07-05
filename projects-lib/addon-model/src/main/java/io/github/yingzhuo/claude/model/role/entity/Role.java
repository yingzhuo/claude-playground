package io.github.yingzhuo.claude.model.role.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
@Schema(description = "角色实体")
public class Role implements Serializable {

    /**
     * 数据库ID
     */
    @Schema(description = "数据库ID")
    @TableId
    private String id;

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    private String name;
}
