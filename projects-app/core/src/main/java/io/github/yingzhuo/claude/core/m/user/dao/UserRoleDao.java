package io.github.yingzhuo.claude.core.m.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yingzhuo.claude.model.role.entity.UserRoleRef;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleRef> {

	@Select("SELECT r.name FROM t_role r JOIN t_user_role ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
	List<String> findRoleNamesByUserId(String userId);

	@Insert("INSERT INTO t_user_role (user_id, role_id) VALUES (#{userId}, #{roleId})")
	int insertUserRole(String userId, String roleId);
}
