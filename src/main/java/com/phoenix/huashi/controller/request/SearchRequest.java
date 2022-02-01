package com.phoenix.huashi.controller.request;


import com.phoenix.huashi.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("SearchRequest 搜索请求")
public class SearchRequest {
    @ApiModelProperty("搜索内容")
    private String contents;

    @Min(value = 1,message = "type字段必须为1或2或3" )
    @Max(value = 3,message = "type字段必须为1或2或3" )
    @ApiModelProperty("搜索类型（1为通知公告，2为展示项目，3为招募项目）")
    Integer type;

    @ApiModelProperty("学院")
    private String department;

    @NotNull
    @ApiModelProperty("分页参数")
    private PageParam pageParam;
}
