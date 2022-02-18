package com.phoenix.huashi.dto.recruitproject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BriefProjectInformation 我的组队简要信息")
public class BriefProjectInformation{
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("标签一")
    private String tag1;
    @ApiModelProperty("标签二")
    private String tag2;
    @ApiModelProperty("标签三")
    private String tag3;

    @ApiModelProperty("负责人姓名")
    private String principalName;

    @ApiModelProperty("立项学院")
    private String institution;

    @ApiModelProperty("状态")
    private Integer status;
}
