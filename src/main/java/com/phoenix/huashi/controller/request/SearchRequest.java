package com.phoenix.huashi.controller.request;


import com.phoenix.huashi.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("SearchRequest 搜索请求")
public class SearchRequest {
    @Min(value = 1, message = "type字段必须为1或2或3或4")
    @Max(value = 4, message = "type字段必须为1或2或3或4")
    @ApiModelProperty("搜索类型（1为通知公告，2为展示项目，3为招募项目，4为新闻）")
    Integer type;

    @Min(value = 0, message = "type字段必须为0或1")
    @Max(value = 1, message = "type字段必须为0或1")
    @ApiModelProperty("招募类型(仅招募项目时选) 0为学生招募 1为教师招募 不填则全选")
    Integer recruitType;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("学院")
    private String department;

    @ApiModelProperty("项目负责人")
    private String captain;

    @ApiModelProperty("项目标签")
    private String tag;

    @ApiModelProperty("年份")
    private String year;

    @Min(value = 0, message = "type字段必须为0或1")
    @Max(value = 1, message = "type字段必须为0或1")
    @ApiModelProperty("排序方式 0为时间排序 1为热度排序 ")
    private Integer sortType;

    @Min(value = 0, message = "type字段必须为0或1")
    @Max(value = 1, message = "type字段必须为0或1")
    @ApiModelProperty("顺序 0顺序 1倒序")
    private Integer order;


    @NotNull
    @ApiModelProperty("分页参数")
    private PageParam pageParam;
}
