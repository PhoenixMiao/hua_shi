package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Vote 投票")
public class Vote {
    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("用户创赛号")
    private String chuangNUm;
}
