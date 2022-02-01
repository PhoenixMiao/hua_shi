package com.phoenix.huashi.controller.response;

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
@ApiModel("SearchResponse 搜索结果列表")
public class SearchResponse {
    @ApiModelProperty("结果类型（1为通知公告，2为展示项目，3为招募项目）")
    private Integer type;

    @ApiModelProperty("对象id")
    private Long id;

    @ApiModelProperty("通知公告或项目的名称")
    private String title;

    @ApiModelProperty("结果子类型（通知公告的类型或项目的类型）")
    private String subtype;

    @ApiModelProperty("附加信息一：通知公告为信息来源，项目为负责人姓名")
    private String attachment1;

    @ApiModelProperty("附加信息二：通知公告为发布日期，项目为立项院系")
    private String attachment2;
}
