package io.github.yingzhuo.claude.exception;

/**
 * 业务异常。当 Service 层检测到业务规则冲突时抛出，
 * 由 {@code ControllerExceptionHandlers} 统一转换为对应 HTTP 响应。
 *
 * @author 应卓
 */
public class BusinessException extends RuntimeException {

    /**
     * 创建业务异常
     *
     * @param message 异常描述（将作为响应消息返回给客户端）
     */
    public BusinessException(String message) {
        super(message);
    }
}
