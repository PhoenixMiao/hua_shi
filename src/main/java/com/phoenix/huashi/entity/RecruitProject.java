package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RecruitProject 招募项目")
public class RecruitProject implements Serializable {
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("负责人创赛号")
    private String captainChuangNum;

    @ApiModelProperty("负责人姓名")
    private String captainName;

    @ApiModelProperty("立项学院")
    private String institute;

    @ApiModelProperty("项目介绍")
    private String introduction;

    @ApiModelProperty("要求简述")
    private String briefDemand;

    @ApiModelProperty("指导老师姓名")
    private String teacherName;

    @ApiModelProperty("指导老师院系")
    private String teacherApartment;

    @ApiModelProperty("指导老师职称")
    private String teacherRank;

    @ApiModelProperty("预计起始日期")
    private String planStartTime;

    @ApiModelProperty("预计截止日期")
    private String planEndTime;

    @ApiModelProperty("开始招募时间")
    private String recruitTime;

    @ApiModelProperty("项目开始进行时间")
    private String startTime;

    @ApiModelProperty("项目结束时间")
    private String endTime;

    @ApiModelProperty("状态更新时间")
    private String stateUpdateTime;

    @ApiModelProperty("要求详情")
    private String demand;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("需招募人数")
    private Long recruitNum;

    @ApiModelProperty("标签一")
    private String tag1;

    @ApiModelProperty("标签二")
    private String tag2;

    @ApiModelProperty("标签三")
    private String tag3;

    @ApiModelProperty("已有人数")
    private Long memberNum;

    @ApiModelProperty("展示项目Id")
    private Long displayId;
}
