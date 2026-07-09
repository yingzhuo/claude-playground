-- @formatter:off
insert into `t_admin` (`id`, `username`, `password`, `role`)
select '00000000000000000000000000000001', 'superman', '{noop}superman@123', 'SUPER'
where not exists (select 1 from `t_admin` where `username` = 'superman');
-- @formatter:on
