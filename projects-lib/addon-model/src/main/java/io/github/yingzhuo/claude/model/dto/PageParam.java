package io.github.yingzhuo.claude.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 分页请求参数基类
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema(description = "分页请求参数基类")
public abstract class PageParam {

	@Min(value = 1, message = "页码最小为{value}")
	@Max(value = 10000, message = "页码最大为{value}")
	@Builder.Default
	@Schema(description = "页码(从1开始)", minimum = "1", maximum = "10000", defaultValue = "1")
	private int pageNumber = 1;

	@Min(value = 10, message = "每页条目数最小为{value}")
	@Max(value = 500, message = "每页条目数最小为{value}")
	@Builder.Default
	@Schema(description = "每页条目数", minimum = "10", maximum = "500", defaultValue = "10")
	private int pageSize = 10;

}
