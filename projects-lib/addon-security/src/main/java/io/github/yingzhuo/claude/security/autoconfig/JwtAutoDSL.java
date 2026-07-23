package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.filter.JwtAuthFilter;
import io.github.yingzhuo.claude.security.jwt.JwtBlacklistChecker;
import io.github.yingzhuo.claude.security.jwt.JwtVerifier;
import io.github.yingzhuo.claude.security.jwt.TokenResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAutoDSL extends AbstractHttpConfigurer<JwtAutoDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        var applicationContext = http.getSharedObject(ApplicationContext.class);

        var tokenResolver = applicationContext.getBean(TokenResolver.class);
        var jwtVerifier = applicationContext.getBean(JwtVerifier.class);
        var jwtAuthFilter = new JwtAuthFilter(tokenResolver, jwtVerifier);

        var blacklistChecker = applicationContext.getBeanProvider(JwtBlacklistChecker.class).getIfAvailable();
        jwtAuthFilter.setBlacklistChecker(blacklistChecker);

        http.addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter.class); // 认证
    }

}
