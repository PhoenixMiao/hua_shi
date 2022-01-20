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

    @ApiModelProperty("项目负责人")
    private Long principal;

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

    @ApiModelProperty("论文成果")
    private String paper;

    @ApiModelProperty("截止日期")
    private String deadline;

    @ApiModelProperty("人数上限")
    private Long personLimit;

    @ApiModelProperty("状态")
    private Integer status;
}
