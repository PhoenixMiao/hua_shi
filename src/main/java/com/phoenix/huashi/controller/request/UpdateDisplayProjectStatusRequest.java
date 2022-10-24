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
@ApiModel("UpdateDisplayProjectRequest 更新普通项目审核状态")
public class UpdateDisplayProjectStatusRequest {
    @NotNull
    @ApiModelProperty("项目id")
    private Long projectId;

    @NotNull
    @ApiModelProperty("状态：ACCEPT通过，REFUSE未通过")
    private String status;

    @ApiModelProperty("审核理由（仅审核未通过时）")
    private String reason;

}
