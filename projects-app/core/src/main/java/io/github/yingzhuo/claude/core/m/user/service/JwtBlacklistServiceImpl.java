package io.github.yingzhuo.claude.core.m.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yingzhuo.claude.core.m.user.dao.JwtBlacklistDao;
import io.github.yingzhuo.claude.model.jwtblacklist.entity.JwtBlacklist;
import io.github.yingzhuo.claude.utility.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

	private final JwtBlacklistDao jwtBlacklistDao;

	@Override
	@Transactional
	public void add(String jti, LocalDateTime expiredAt) {
		var entity = new JwtBlacklist();
		entity.setId(UUIDUtils.randomUUIDv7());
		entity.setTokenJti(jti);
		entity.setExpiredAt(expiredAt);
		entity.setCreatedAt(LocalDateTime.now());
		jwtBlacklistDao.insert(entity);
		log.debug("JWT 已加入黑名单: jti={}", jti);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isBlacklisted(String jti) {
		var count = jwtBlacklistDao.selectCount(
			new LambdaQueryWrapper<JwtBlacklist>()
				.eq(JwtBlacklist::getTokenJti, jti)
		);
		return count != null && count > 0;
	}

	@Override
	@Transactional
	public int purgeExpired() {
		var wrapper = new LambdaQueryWrapper<JwtBlacklist>()
			.lt(JwtBlacklist::getExpiredAt, LocalDateTime.now());
		var count = jwtBlacklistDao.delete(wrapper);
		if (count > 0) {
			log.debug("已清理 {} 条过期的 JWT 黑名单记录", count);
		}
		return count;
	}
}
