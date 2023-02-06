package com.phoenix.huashi.dto.news;

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
@ApiModel("BriefNews 新闻简要信息")
public class BriefNews {

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发布日期")
    private String publishDate;

    @ApiModelProperty("图片")
    private String picture;

}
