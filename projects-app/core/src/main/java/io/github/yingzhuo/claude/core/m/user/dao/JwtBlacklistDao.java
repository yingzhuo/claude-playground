package io.github.yingzhuo.claude.core.m.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yingzhuo.claude.model.jwtblacklist.entity.JwtBlacklist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JwtBlacklistDao extends BaseMapper<JwtBlacklist> {
}
