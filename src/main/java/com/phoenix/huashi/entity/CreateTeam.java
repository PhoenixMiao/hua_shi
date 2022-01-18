package com.phoenix.huashi.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("CreateTeam 创建团队申请")

public class CreateTeam {
    @Id
    @ApiModelProperty("申请id")
    private Long id;

    @ApiModelProperty("比赛名称")
    private String name;

    @ApiModelProperty("项目简介")
    private String introduction;

    @ApiModelProperty("负责人")
    private Long principal;

    @ApiModelProperty("已有成员介绍")
    private String current_number;

    @ApiModelProperty("需求成员情况")
    private String member_required;

    @ApiModelProperty("是否允许")
    private Integer allow;
}
