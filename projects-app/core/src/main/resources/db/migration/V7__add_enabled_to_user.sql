-- @formatter:off
alter table `t_user`
    add column `enabled` tinyint(1) not null default 1 comment '启用状态（1=启用，0=禁用）';
-- @formatter:on
