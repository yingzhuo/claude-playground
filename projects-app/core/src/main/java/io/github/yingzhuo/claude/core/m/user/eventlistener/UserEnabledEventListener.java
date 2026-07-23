package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.dao.UserDao;
import io.github.yingzhuo.claude.model.event.UserEnabledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户启用/禁用事件监听器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserEnabledEventListener {

    private final UserDao userDao;

    /**
     * 处理用户启用/禁用事件
     *
     * @param event 用户启用/禁用事件
     */
    @EventListener
    @Transactional
    public void handleUserEnabled(UserEnabledEvent event) {
        var user = userDao.selectById(event.userId());
        if (user == null) {
            log.warn("用户不存在，无法设置启用状态: userId={}", event.userId());
            return;
        }
        user.setEnabled(event.enabled());
        userDao.updateById(user);
        log.debug("用户启用状态已更新: userId={}, enabled={}", event.userId(), event.enabled());
    }
}
