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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("AssignWorkRequest 分配工作请求")
public class AssignWorkRequest {
    @NotNull
    @ApiModelProperty("项目id")
    Long projectId;

    @NotNull
    @ApiModelProperty("成员创赛号")
    String memberChuangNum;

    @ApiModelProperty("分工内容")
    String work;
}
