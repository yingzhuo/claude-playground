package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.dao.UserDao;
import io.github.yingzhuo.claude.model.event.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户删除事件监听器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeletedEventListener {

    private final UserDao userDao;

    /**
     * 处理用户删除事件
     *
     * @param event 用户删除事件
     */
    @EventListener
    @Transactional
    public void handleUserDeleted(UserDeletedEvent event) {
        var deleted = userDao.deleteById(event.userId());
        if (deleted > 0) {
            log.debug("用户已删除: userId={}", event.userId());
        } else {
            log.warn("用户不存在，无法删除: userId={}", event.userId());
        }
    }
}
