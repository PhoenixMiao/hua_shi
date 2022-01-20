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
@ApiModel("Notification 通知公告")
public class Notification implements Serializable {
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("信息来源")
    private String source;

    @ApiModelProperty("发布日期")
    private String publishDate;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("图片")
    private String picture;


}
