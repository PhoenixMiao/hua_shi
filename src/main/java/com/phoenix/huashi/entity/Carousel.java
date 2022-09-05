package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Carousel 轮播图")
public class Carousel {
    @Id
    @ApiModelProperty("id")
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("图片url")
    private String url;

    @ApiModelProperty("对应项目id")
    private Long projectId;

    @ApiModelProperty("对应项目类型(0招募项目 1展示项目 2通知公告)")
    private Integer projectType;

    @ApiModelProperty("上传时间")
    private String uploadTime;

    @ApiModelProperty("状态 0审核中 1通过 -1未通过")
    private Integer status;
}
