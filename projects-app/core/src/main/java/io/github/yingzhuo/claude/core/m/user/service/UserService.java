/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.core.m.user.service;

import io.github.yingzhuo.claude.model.user.entity.User;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author 应卓
 */
public interface UserService {

	/**
	 * 查询所有用户
	 *
	 * @return 用户列表
	 */
	List<User> findAll();

	/**
	 * 根据ID查询用户
	 *
	 * @param id 数据库ID
	 * @return 用户，未找到返回 {@code null}
	 */
	User findById(String id);

	/**
	 * 创建用户
	 *
	 * @param user 用户实体
	 */
	void create(User user);

	/**
	 * 更新用户
	 *
	 * @param user 用户实体
	 */
	void update(User user);

	/**
	 * 根据ID删除用户
	 *
	 * @param id 数据库ID
	 */
	void deleteById(String id);
}
