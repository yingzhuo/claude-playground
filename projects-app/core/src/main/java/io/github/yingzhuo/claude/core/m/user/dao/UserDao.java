package io.github.yingzhuo.claude.core.m.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yingzhuo.claude.model.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问对象
 *
 * @author 应卓
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
