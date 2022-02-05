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
@ApiModel("InviteUserRequest 邀请用户加入项目请求")
public class InviteUserRequest {

    @ApiModelProperty("用户创赛号")
    private String userChuangNum;

    @NotNull
    @ApiModelProperty("项目Id")
    private Long projectId;
}
