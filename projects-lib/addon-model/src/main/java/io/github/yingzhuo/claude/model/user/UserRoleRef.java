package io.github.yingzhuo.claude.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户角色关联实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_role")
@Schema(description = "用户角色关联实体")
public class UserRoleRef implements Serializable {

	@Schema(description = "用户ID")
	private String userId;

	@Schema(description = "角色ID")
	private String roleId;
}
