-- ------------------------------------------------------------------------------------------------------------------
-- 用户表：添加注销时间字段
-- ------------------------------------------------------------------------------------------------------------------

-- @formatter:off
alter table `t_user`
    add column `cancelled_at` datetime null comment '注销时间：非空表示用户已主动注销' after `created_at`;

create index `idx_t_user_cancelled_at`
    on `t_user` (`cancelled_at`);
-- @formatter:on
