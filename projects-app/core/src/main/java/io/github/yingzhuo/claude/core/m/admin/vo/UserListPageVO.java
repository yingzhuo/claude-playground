package io.github.yingzhuo.claude.core.m.admin.vo;

import io.github.yingzhuo.claude.model.vo.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Schema(description = "用户列表分页结果")
public class UserListPageVO extends PageResult<UserListItemVO> {
}
