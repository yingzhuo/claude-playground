package io.github.yingzhuo.claude.model.webmvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "API统一返回格式")
public final class R<T> implements Serializable {

	@Schema(description = "结果码", example = "200")
	private final String code;

	@Schema(description = "结果消息", example = "操作成功")
	private final String msg;

	@Nullable
	@Schema(description = "数据")
	private final T data;

	public static <T> R<T> ok(@Nullable T data) {
		return new R<>("200", "操作成功", data);
	}

	public static R<Void> ok() {
		return new R<>("200", "操作成功", null);
	}

	public static R<Void> error400(String msg) {
		return new R<>("400", msg, null);
	}

	public static R<Void> error401(String msg) {
		return new R<>("401", msg, null);
	}

	public static R<Void> error403(String msg) {
		return new R<>("403", msg, null);
	}

	public static R<Void> error404(String msg) {
		return new R<>("404", msg, null);
	}

	public static R<Void> error429(String msg) {
		return new R<>("429", msg, null);
	}

	public static R<Void> error500(String msg) {
		return new R<>("500", msg, null);
	}

	public static R<Void> error501() {
		return new R<>("501", "未实现", null);
	}

}
