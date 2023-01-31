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
    @ApiModelProperty("招募类型 0为学生发布 1为教师发布")
    private Integer recruitType;

    @NotNull
    @ApiModelProperty("项目类型 0为创新训练 1为创业训练")
    private Integer type;

    @ApiModelProperty("负责人创赛号")
    private String captainChuangNum;
    @NotNull
    @ApiModelProperty("要求简述")
    private String briefDemand;
    @NotNull
    @ApiModelProperty("要求详情")
    private String demand;

    @ApiModelProperty("指导老师/发布教师姓名")
    private String teacherName;

    @ApiModelProperty("指导老师/发布教师院系")
    private String teacherApartment;

    @ApiModelProperty("指导老师/发布教师职称")
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

    @ApiModelProperty("负责人姓名")
    private String captainName;
    @ApiModelProperty("立项院校")
    private String institute;
    @ApiModelProperty("需招募人数")
    private Long recruitNum;
    @ApiModelProperty("已有人数")
    private Long memberNum;

    @ApiModelProperty("教师个人主页(一般仅教师发布有)")
    private String teacherPersonalHomepage;

}
