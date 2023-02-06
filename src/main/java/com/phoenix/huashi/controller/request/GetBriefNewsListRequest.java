package com.phoenix.huashi.controller.request;

import com.phoenix.huashi.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("GetBriefNewsListRequest 获取简要新闻列表")

public class GetBriefNewsListRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    PageParam pageParam;
}
