package com.phoenix.huashi.controller.request;

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
@ApiModel("AddMemberRequest 添加招募项目成员")
public class AddMemberRequest {
    @NotNull
    @ApiModelProperty("项目id")
    Long recruitProjectId;

    @NotNull
    @ApiModelProperty("成员创赛号")
    String memberChuangNum;

    @NotNull
    @ApiModelProperty("成员类型 CAPTAIN队长 MEMBER队员")
    String type;
}
