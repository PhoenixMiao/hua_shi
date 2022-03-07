package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("DisplayProject 展示项目")
public class DisplayProject implements Serializable {
    @Id
    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("立项年份")
    private String year;

    @ApiModelProperty("立项学院")
    private String institute;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目负责人姓名")
    private String captainName;

    @ApiModelProperty("指导老师姓名")
    private String teacherName;

    @ApiModelProperty("指导老师院系")
    private String teacherApartment;

    @ApiModelProperty("指导老师职称")
    private String teacherRank;

    @ApiModelProperty("指导老师研究方向")
    private String teacherStudy;

    @ApiModelProperty("项目上传时间")
    private String uploadTime;

    @ApiModelProperty("项目简介")
    private String introduction;

    @ApiModelProperty("项目类型")
    private String type;

    @ApiModelProperty("学科专业")
    private String major;

    @ApiModelProperty("项目编号")
    private String number;

    @ApiModelProperty("获奖情况")
    private String award;

    @ApiModelProperty("项目创新点")
    private String innovation;

    @ApiModelProperty("点赞数")
    private Long likes;

    @ApiModelProperty("收藏数")
    private Long collections;

    @ApiModelProperty("论文成果")
    private String paper;

    @ApiModelProperty("成员1姓名")
    private String memberOneName;

    @ApiModelProperty("成员1年级")
    private String memberOneGrade;

    @ApiModelProperty("成员1专业")
    private String memberOneMajor;

    @ApiModelProperty("成员2姓名")
    private String memberTwoName;

    @ApiModelProperty("成员2年级")
    private String memberTwoGrade;

    @ApiModelProperty("成员2专业")
    private String memberTwoMajor;

    @ApiModelProperty("成员3姓名")
    private String memberThreeName;

    @ApiModelProperty("成员3年级")
    private String memberThreeGrade;

    @ApiModelProperty("成员3专业")
    private String memberThreeMajor;

    @ApiModelProperty("成员4姓名")
    private String memberFourName;

    @ApiModelProperty("成员4年级")
    private String memberFourGrade;

    @ApiModelProperty("成员4专业")
    private String memberFourMajor;

    @ApiModelProperty("成员5姓名")
    private String memberFiveName;

    @ApiModelProperty("成员5年级")
    private String memberFiveGrade;

    @ApiModelProperty("成员5专业")
    private String memberFiveMajor;
}


