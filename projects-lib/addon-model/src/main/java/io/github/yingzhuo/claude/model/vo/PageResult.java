package io.github.yingzhuo.claude.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 分页结果基类
 *
 * @param <T> 列表元素类型
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema(description = "分页结果基类")
public abstract class PageResult<T> {

    @Schema(description = "当前页码")
    private long pageNumber;

    @Schema(description = "每页条目数")
    private long pageSize;

    @Schema(description = "总条目数")
    private long total;

    @Schema(description = "总页数")
    private long totalPages;

    @Schema(description = "数据列表")
    private List<T> items;

}
