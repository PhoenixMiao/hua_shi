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
@ApiModel("News 新闻")
public class News implements Serializable {

    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发布日期")
    private String publishDate;

    @ApiModelProperty("文本内容")
    private String content;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("年份")
    private String year;

    @ApiModelProperty("日期")
    private String date;

}
