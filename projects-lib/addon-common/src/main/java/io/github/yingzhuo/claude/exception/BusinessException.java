package io.github.yingzhuo.claude.exception;

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
