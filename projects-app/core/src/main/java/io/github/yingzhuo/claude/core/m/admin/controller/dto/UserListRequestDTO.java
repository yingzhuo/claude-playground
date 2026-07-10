package io.github.yingzhuo.claude.core.m.admin.controller.dto;

import io.github.yingzhuo.claude.model.dto.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
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
    @Schema(description = "搜索关键字（按用户名模糊搜索）")
    private String searchKey;

}
