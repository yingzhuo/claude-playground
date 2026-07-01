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

/**
 * 全局统一异常处理。
 * <p>
 * 将 Controller 层抛出的各类异常统一转换为 {@link R} 格式的响应体，
 * 避免向客户端暴露堆栈信息或非标准错误结构。
 * 所有处理器方法均标注 {@code @ResponseBody}，确保返回值被序列化为 JSON。
 * </p>
 *
 * @author 应卓
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandlers {

	private static String collectErrors(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(";"));
	}

	/**
	 * 处理 {@link MethodArgumentNotValidException} 类型的请求体校验异常。
	 * <p>
	 * 当在 {@code @RequestBody} 参数上使用 {@code @Valid} 或 {@code @Validated}
	 * 注解且校验失败时抛出。典型场景：
	 * </p>
	 * <ul>
	 *   <li>DTO 字段上的 {@code @NotBlank}、{@code @Size}、{@code @Pattern} 等校验注解未通过</li>
	 *   <li>嵌套对象的级联校验失败</li>
	 * </ul>
	 * <p>
	 * 校验结果从 {@link BindingResult#getFieldErrors()} 中提取所有字段的默认错误消息，
	 * 以 {@code "; "} 拼接后返回。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>各字段校验失败消息（以 ";" 分隔）</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 请求体校验异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		var msg = collectErrors(e.getBindingResult());
		log.warn("参数校验失败: {}", msg);
		return R.error400(msg);
	}

	/**
	 * 处理 {@link BindException} 类型的参数绑定异常。
	 * <p>
	 * 当使用 {@code @ModelAttribute} 或普通表单参数绑定时校验失败抛出。
	 * 与 {@link MethodArgumentNotValidException} 的区别在于触发场景不同：
	 * 前者针对 JSON 请求体，后者针对表单/模型属性绑定。
	 * </p>
	 * <p>
	 * 校验结果从 {@link BindingResult#getFieldErrors()} 中提取所有字段的默认错误消息，
	 * 以 {@code ";"} 拼接后返回。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>各字段绑定错误消息（以 ";" 分隔）</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 参数绑定异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public R<Void> handleBindException(BindException e) {
		var msg = collectErrors(e.getBindingResult());
		log.warn("参数绑定失败: {}", msg);
		return R.error400(msg);
	}

	/**
	 * 处理 {@link ConstraintViolationException} 类型的约束违规异常。
	 * <p>
	 * 当在方法参数（非 RequestBody）上使用 {@code @Validated} 注解时，
	 * 校验失败会抛出此异常。常见场景：
	 * </p>
	 * <ul>
	 *   <li>Controller 方法参数上的 {@code @RequestParam @Positive}、{@code @PathVariable @Min} 等直接参数校验</li>
	 *   <li>Service 层方法上的 {@code @Validated} 参数校验</li>
	 * </ul>
	 * <p>
	 * 从 {@code getConstraintViolations()} 中提取所有违规消息，
	 * 以 {@code ";"} 拼接后返回。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>各约束违规消息（以 ";" 分隔）</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 约束违规异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
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

	/**
	 * 处理 {@link MissingServletRequestParameterException} 类型的缺少请求参数异常。
	 * <p>
	 * 当 Controller 方法声明了必填的 {@code @RequestParam} 参数但请求未携带时抛出。
	 * 日志记录缺失的参数名，响应消息明确告知客户端缺少的具体参数名称。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>"缺少请求参数: {参数名}"</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 缺少请求参数异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R<Void> handleMissingParam(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数: {}", e.getParameterName());
		return R.error400("缺少请求参数: " + e.getParameterName());
	}

	/**
	 * 处理 {@link MethodArgumentTypeMismatchException} 类型的参数类型不匹配异常。
	 * <p>
	 * 当请求参数值无法转换为 Controller 方法参数声明的目标类型时抛出。
	 * 例如：期望 {@code Integer} 类型但收到无法解析的字符串值（如 {@code "abc"}）。
	 * 日志记录参数名和目标类型，响应消息告知客户端参数名。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>"参数类型不匹配: {参数名}"</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 参数类型不匹配异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public R<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		log.warn("参数类型不匹配: name={}, requiredType={}", e.getName(), e.getRequiredType());
		return R.error400("参数类型不匹配: " + e.getName());
	}

	/**
	 * 处理 {@link BusinessException} 类型的业务异常。
	 * <p>
	 * 当 Service 层检测到业务规则冲突（如旧密码错误、密码不满足复杂度要求等）时抛出。
	 * 所有业务异常统一返回 400 Bad Request，消息内容来自异常创建时指定的描述。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>异常消息原文</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 业务异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public R<Void> handleBusinessException(BusinessException e) {
		log.warn("业务异常: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	/**
	 * 处理 {@link IllegalArgumentException} 类型的非法参数异常。
	 * <p>
	 * 作为业务层参数校验的兜底处理器。当 Service 层或工具类检测到
	 * 传入参数不符合预期时抛出。
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>异常消息原文</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 非法参数异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public R<Void> handleIllegalArgument(IllegalArgumentException e) {
		log.warn("非法参数: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	/**
	 * 处理 {@link AccessDeniedException} 类型的访问被拒绝异常。
	 * <p>
	 * 当请求已通过认证但缺少所需权限时抛出。常见场景：
	 * </p>
	 * <ul>
	 *   <li>Controller 方法上 {@code @PreAuthorize("hasRole('ADMIN')")} 权限不足</li>
	 *   <li>Service 方法上 {@code @Secured("ROLE_ADMIN")} 权限不足</li>
	 * </ul>
	 * <p>
	 * <strong>注意：</strong>仅捕获方法安全注解（{@code @PreAuthorize}、{@code @Secured}）触发的
	 * 权限拒绝。Spring Security 过滤器链（Filter Chain）中的未认证/未授权请求
	 * 由 {@code AuthenticationEntryPoint} / {@code AccessDeniedHandler} 处理，
	 * 不会进入此方法。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>403 Forbidden</td></tr>
	 *     <tr><td>code</td><td>403</td></tr>
	 *     <tr><td>msg</td><td>异常消息原文（通常包含所需权限信息）</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 访问被拒绝异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public R<Void> handleAccessDenied(AccessDeniedException e) {
		log.warn("访问被拒绝: {}", e.getMessage());
		return R.error403(e.getMessage());
	}

	/**
	 * 处理 {@link HttpRequestMethodNotSupportedException} 类型的请求方法不支持异常。
	 * <p>
	 * 当客户端使用 HTTP 方法（如 POST）访问仅支持其他方法（如 GET）的端点时抛出。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>400 Bad Request</td></tr>
	 *     <tr><td>code</td><td>400</td></tr>
	 *     <tr><td>msg</td><td>异常消息原文</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e 请求方法不支持异常实例
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.warn("请求方法不支持: {}", e.getMessage());
		return R.error400(e.getMessage());
	}

	/**
	 * 兜底处理器，处理所有未被其他 {@code @ExceptionHandler} 方法捕获的异常。
	 * <p>
	 * 作为异常处理链的最后一环，防止未预期异常泄漏到客户端导致 500 页空白或堆栈信息暴露。
	 * 优先匹配原则：Spring 会按异常类型精确匹配，子类优先于父类，
	 * 因此此方法仅在无其他匹配处理器时触发。
	 * </p>
	 * <p>
	 * <strong>安全考量：</strong>响应消息使用固定字符串 "服务器内部错误"，
	 * 不将异常消息返回给客户端，避免信息泄露。
	 * 异常完整堆栈以 ERROR 级别记录到日志，包含请求方法和路径以便排查。
	 * </p>
	 *
	 * <table border="1" summary="响应说明">
	 *   <caption>响应说明</caption>
	 *   <thead>
	 *     <tr><th>属性</th><th>值</th></tr>
	 *   </thead>
	 *   <tbody>
	 *     <tr><td>HTTP Status</td><td>500 Internal Server Error</td></tr>
	 *     <tr><td>code</td><td>500</td></tr>
	 *     <tr><td>msg</td><td>"服务器内部错误"（固定值，不泄露原始异常信息）</td></tr>
	 *   </tbody>
	 * </table>
	 *
	 * @param e       异常实例（可能为任何未捕获的 {@link Throwable}）
	 * @param request 当前 HTTP 请求（用于日志记录请求方法和 URI）
	 * @return 包含错误信息的 {@link R} 响应体
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public R<Void> handleThrowable(Throwable e, HttpServletRequest request) {
		log.error("服务器内部错误 [{} {}]", request.getMethod(), request.getRequestURI(), e);
		return R.error500("服务器内部错误");
	}

}
