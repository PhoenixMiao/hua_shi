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
@ApiModel("JoinTeam 加入团队申请")
public class JoinTeam {
    @Id
    @ApiModelProperty("申请id")
    private Long id;

    @ApiModelProperty("申请人id")
    private Long personid;

    @ApiModelProperty("项目id")
    private Long projectid;

    @ApiModelProperty("是否通过")
    private Integer allow;
}
