-- @formatter:off
create table if not exists `t_admin`
(
    `id`              char(32)   not null comment '主键',
    `username`        varchar(20)  not null comment '用户名',
    `password`        varchar(100) not null comment '密码',
    `role`            varchar(20)  not null comment '角色（NORMAL / SUPER）',
    `last_login_time` datetime     null comment '最后登录时间',
    primary key (`id`),
    index `idx_username` (`username`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '后台管理员表';

-- 种子数据
insert into `t_admin` (`id`, `username`, `password`, `role`) values
('00000000000000000000000000000001', 'superman', '{noop}superman@123', 'SUPER');
-- @formatter:on
