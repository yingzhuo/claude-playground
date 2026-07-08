-- @formatter:off
alter table `t_user`
    add column `email`     varchar(50)  null after `nickname`,
    add column `avatar_url` varchar(300) null after `email`;
-- @formatter:on
