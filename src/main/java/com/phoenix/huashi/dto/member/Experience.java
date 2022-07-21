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
@ApiModel("Experience 项目经历")
public class Experience {
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("类别")
    private String type;

    @ApiModelProperty("项目类型")
    private Integer projectType;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("创赛号")
    private String chuangNum;

    @ApiModelProperty("分工")
    private String work;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目介绍")
    private String introduction;

    @ApiModelProperty("项目开始进行时间")
    private String startTime;
}
