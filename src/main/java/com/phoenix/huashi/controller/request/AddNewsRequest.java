package com.phoenix.huashi.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AddNewsRequest 新增新闻")
public class AddNewsRequest {

    @ApiModelProperty("标题")
    private String title;


}
