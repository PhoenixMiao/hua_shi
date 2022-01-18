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
@ApiModel("Notification 通知公告")
public class Notification {
    @Id
    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("比赛简介")
    private String introduction;

    @ApiModelProperty("报名方式")
    private String register_way;

    @ApiModelProperty("时间节点")
    private String timepoint;

    @ApiModelProperty("参赛要求")
    private String requirement;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("状态")
    private Integer status;
}
