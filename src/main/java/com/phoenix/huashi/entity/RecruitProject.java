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
@ApiModel("RecruitProject 招募项目")
public class RecruitProject implements Serializable {
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("负责人创赛号")
    private String captain_chuang_num;

    @ApiModelProperty("要求简述")
    private String brief_demand;

    @ApiModelProperty("要求详情")
    private String demand;

    @ApiModelProperty("指导老师姓名")
    private String teacher_name;

    @ApiModelProperty("指导老师院系")
    private String teacher_apartment;

    @ApiModelProperty("指导老师职称")
    private String teacher_rank;

    @ApiModelProperty("预计起始日期")
    private String plan_start_time;

    @ApiModelProperty("预计截止日期")
    private String plan_end_time;

    @ApiModelProperty("项目介绍")
    private String introduction;

    @ApiModelProperty("标签一")
    private String tag1;

    @ApiModelProperty("标签二")
    private String tag2;

    @ApiModelProperty("标签三")
    private String tag3;

    @ApiModelProperty("状态更新时间")
    private String state_update_time;

    @ApiModelProperty("开始招募时间")
    private String recruit_time;

    @ApiModelProperty("项目开始进行时间")
    private String start_time;

    @ApiModelProperty("项目结束时间")
    private String end_time;

    @ApiModelProperty("负责人姓名")
    private String captain_name;

    @ApiModelProperty("立项学院")
    private String institute;

    @ApiModelProperty("需招募人数")
    private Long recruit_num;

    @ApiModelProperty("已有人数")
    private Long member_num;
}
