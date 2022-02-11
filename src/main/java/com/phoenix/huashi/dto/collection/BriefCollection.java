package com.phoenix.huashi.dto.collection;

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
@ApiModel("BriefCollection 展示收藏夹简要信息")
public class BriefCollection {
    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目负责人姓名")
    private String principal;

    @ApiModelProperty("项目类型")
    private String type;

    @ApiModelProperty("立项学院")
    private String department;
}
