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
@ApiModel("BriefDisplayVoteProject 展示项目投票简要信息")
public class BriefDisplayVoteProject {
    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目负责人姓名")
    private String principalName;

    @ApiModelProperty("项目类型")
    private String type;

    @ApiModelProperty("立项学院")
    private String institute;

    @ApiModelProperty("投票数")
    private Long vote;
}
