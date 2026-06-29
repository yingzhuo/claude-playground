-- ------------------------------------------------------------------------------------------------------------------
-- 用户表
-- ------------------------------------------------------------------------------------------------------------------

-- @formatter:off

drop table if exists `t_user`;
create table if not exists `t_user`
(
    `id`         char(32)    not null comment '数据库ID',
    `username`   varchar(32) not null comment '用户名',
    `password`   varchar(64) not null comment '密码',
    `dob`        date        null comment '出生日期',
    `gender`     varchar(16) null comment '性别 (MALE / FEMALE / UNKNOWN)',
    `created_at` datetime    not null comment '记录创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci
  comment = '用户表';

-- @formatter:on
