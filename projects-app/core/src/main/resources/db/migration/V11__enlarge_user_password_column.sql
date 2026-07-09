-- @formatter:off
alter table `t_user`
    modify column `password` varchar(128) not null comment '密码';
-- @formatter:on
