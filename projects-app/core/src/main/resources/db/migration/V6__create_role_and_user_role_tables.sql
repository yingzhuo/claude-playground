-- ------------------------------------------------------------------------------------------------------------------
-- 角色表 / 用户角色关联表
-- ------------------------------------------------------------------------------------------------------------------

-- @formatter:off
create table if not exists `t_role`
(
    `id`   char(32)    not null comment '数据库ID',
    `name` varchar(32) not null comment '角色名',
    primary key (`id`),
    unique key `uk_role_name` (`name`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '角色表';

create table if not exists `t_user_role`
(
    `user_id` char(32) not null comment '用户ID',
    `role_id` char(32) not null comment '角色ID',
    primary key (`user_id`, `role_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '用户角色关联表';

-- 种子数据
insert ignore into `t_role` (`id`, `name`)
values ('00000000000000000000000000000001', 'ROLE_USER'),
       ('00000000000000000000000000000002', 'ROLE_VIP');
-- @formatter:on
