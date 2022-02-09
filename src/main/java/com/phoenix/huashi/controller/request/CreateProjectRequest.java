package com.phoenix.huashi.controller.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("CreateProjectRequest 创建队伍")
public class CreateProjectRequest {
    @NotNull
    @ApiModelProperty("项目名称")
    private String name;
    @NotNull
    @ApiModelProperty("负责人创赛号")
    private String captainChuangNum;
    @NotNull
    @ApiModelProperty("要求简述")
    private String briefDemand;
    @NotNull
    @ApiModelProperty("要求详情")
    private String demand;

    @ApiModelProperty("指导老师姓名")
    private String teacherName;

    @ApiModelProperty("指导老师院系")
    private String teacherApartment;

    @ApiModelProperty("指导老师职称")
    private String teacherRank;
    @NotNull
    @ApiModelProperty("预计起始日期")
    private String planStartTime;
    @NotNull
    @ApiModelProperty("预计截止日期")
    private String planEndTime;
    @NotNull
    @ApiModelProperty("项目介绍")
    private String introduction;

    @ApiModelProperty("标签一")
    private String tag1;

    @ApiModelProperty("标签二")
    private String tag2;

    @ApiModelProperty("标签三")
    private String tag3;



    @ApiModelProperty("开始招募时间")
    private String recruitTime;

    @ApiModelProperty("项目开始进行时间")
    private String startTime;

    @ApiModelProperty("项目结束时间")
    private String endTime;
    @ApiModelProperty("负责人姓名")
    private String captainName;
    @ApiModelProperty("立项院校")
    private String institute;
    @ApiModelProperty("需招募人数")
    private Long recruitNum;
    @ApiModelProperty("已有人数")
    private Long memberNum;
}
