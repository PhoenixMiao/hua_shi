package com.phoenix.huashi.dto.member;

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
@ApiModel("BriefMember 项目成员简要信息")
public class BriefMember {
    @ApiModelProperty("创赛号")
    private String chuangNum;

    @ApiModelProperty("分工")
    private String work;
}
