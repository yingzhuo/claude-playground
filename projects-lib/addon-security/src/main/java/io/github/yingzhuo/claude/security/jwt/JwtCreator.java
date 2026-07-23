package io.github.yingzhuo.claude.security.jwt;

import io.github.yingzhuo.claude.model.admin.Admin;
import io.github.yingzhuo.claude.model.user.User;

public interface JwtCreator {

    String create(User user);

    String create(Admin admin);

}
