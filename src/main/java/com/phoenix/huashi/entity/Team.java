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
@ApiModel("Team 团队")
public class Team {
    @Id
    @ApiModelProperty("团队id")
    private Long id;

    @ApiModelProperty("项目成员")
    private String member;

    @ApiModelProperty("是否招募人员")
    private Integer recruit;

    @ApiModelProperty("需求成员情况")
    private String member_required;

    @ApiModelProperty("项目成员id")
    private String members_id;
}
