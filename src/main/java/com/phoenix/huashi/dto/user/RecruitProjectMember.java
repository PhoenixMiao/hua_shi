package com.phoenix.huashi.dto.user;

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
@ApiModel("RecruitProjectMember 招募项目成员信息")
public class RecruitProjectMember {
    @Id
    @ApiModelProperty("创赛号")
    private String ChuangNum;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("QQ号")
    private String QQ;

    @ApiModelProperty("微信号")
    private String wechatNum;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("分工")
    private String work;
}
