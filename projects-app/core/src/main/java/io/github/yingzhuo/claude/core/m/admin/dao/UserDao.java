package io.github.yingzhuo.claude.core.m.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yingzhuo.claude.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminUserDao")
public interface UserDao extends BaseMapper<User> {
}
