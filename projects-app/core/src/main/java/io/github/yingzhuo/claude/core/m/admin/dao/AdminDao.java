package io.github.yingzhuo.claude.core.m.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yingzhuo.claude.model.admin.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao extends BaseMapper<Admin> {
}
