package io.github.yingzhuo.claude.model.admin;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 管理员角色
 */
@Schema(description = "管理员角色")
public enum AdminRole {

    /**
     * 普通管理员
     */
    NORMAL,

    /**
     * 超级管理员
     */
    SUPER

}
