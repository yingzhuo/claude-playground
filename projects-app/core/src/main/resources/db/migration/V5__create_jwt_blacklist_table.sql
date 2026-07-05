-- ------------------------------------------------------------------------------------------------------------------
-- JWT 黑名单表
-- ------------------------------------------------------------------------------------------------------------------

-- @formatter:off
create table if not exists `t_jwt_blacklist`
(
    `id`         char(32)  not null comment '主键',
    `token_jti`  char(36)  not null comment 'JWT jti 声明值',
    `expired_at` datetime  not null comment 'token 过期时间，用于清理',
    `created_at` datetime  not null comment '拉黑时间',
    primary key (`id`),
    index `idx_token_jti` (`token_jti`),
    index `idx_expired_at` (`expired_at`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = 'JWT 黑名单表';
-- @formatter:on
