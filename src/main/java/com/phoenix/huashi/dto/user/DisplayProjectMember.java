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
@ApiModel("DisplayProjectMember 展示项目成员信息")
public class DisplayProjectMember {
    @Id
    @ApiModelProperty("创赛号")
    private String ChuangNum;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年级")
    private String grade;

    @ApiModelProperty("专业")
    private String major;
}
