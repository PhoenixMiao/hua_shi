package com.phoenix.huashi.dto.displayproject;

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
@ApiModel("BriefDisplayProject 展示项目简要信息")
public class BriefHomepageDisplayProject {
    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;



    @ApiModelProperty("项目类型")
    private String type;

    @ApiModelProperty("项目介绍")
    private String introduction;


    @ApiModelProperty("获奖情况")
    private String award;

}
