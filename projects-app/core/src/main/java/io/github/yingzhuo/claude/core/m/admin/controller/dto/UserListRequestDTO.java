package io.github.yingzhuo.claude.core.m.admin.controller.dto;

import io.github.yingzhuo.claude.model.dto.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.Nullable;

@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户列表查询请求")
public class UserListRequestDTO extends PageParam {

	@Nullable
	@Schema(description = "搜索关键字(按用户名模糊搜索)")
	@Size(min = 1, max = 20, message = "搜索关键字长度最小{min}最大{max}")
	private String searchKey;

}
