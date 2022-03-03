package com.phoenix.huashi.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateProjectStatusRequest 更新项目状态")
public class UpdateProjectStatusRequest {
    @NotNull
    @ApiModelProperty("项目id")
    private Long projectId;
    @NotNull
    @ApiModelProperty("项目状态 0招募中 1进行中 -1已结束")
    private Integer newStatus;
}
