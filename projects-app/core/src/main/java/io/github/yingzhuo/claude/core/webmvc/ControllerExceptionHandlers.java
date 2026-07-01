package io.github.yingzhuo.claude.core.webmvc;

import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.webmvc.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandlers {

	private static String collectErrors(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(";"));
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		var msg = collectErrors(e.getBindingResult());
		log.warn("参数校验失败: {}", msg);
		return R.error400(msg);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public R<Void> handleBindException(BindException e) {
		var msg = collectErrors(e.getBindingResult());
		log.warn("参数绑定失败: {}", msg);
		return R.error400(msg);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public R<Void> handleConstraintViolation(ConstraintViolationException e) {
		var msg = e.getConstraintViolations().stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.joining(";"));
		log.warn("约束校验失败: {}", msg);
		return R.error400(msg);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R<Void> handleMissingParam(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数: {}", e.getParameterName());
		return R.error400("缺少请求参数: " + e.getParameterName());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public R<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		log.warn("参数类型不匹配: name={}, requiredType={}", e.getName(), e.getRequiredType());
		return R.error400("参数类型不匹配: " + e.getName());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public R<Void> handleBusinessException(BusinessException e) {
		log.warn("业务异常: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public R<Void> handleIllegalArgument(IllegalArgumentException e) {
		log.warn("非法参数: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public R<Void> handleAccessDenied(AccessDeniedException e) {
		log.warn("访问被拒绝: {}", e.getMessage());
		return R.error403(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.warn("请求方法不支持: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public R<Void> handleThrowable(Throwable e, HttpServletRequest request) {
		log.error("服务器内部错误 [{} {}]", request.getMethod(), request.getRequestURI(), e);
		return R.error500("服务器内部错误");
	}

}
