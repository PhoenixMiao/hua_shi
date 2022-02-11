package com.phoenix.huashi.entity;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
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
@ApiModel("Member 成员")
public class Member implements Serializable {
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("类别")
    private String type;

    @ApiModelProperty("项目类型")
    private Integer projectType;

    @ApiModelProperty("项目id")
    private Long projectId;

    @ApiModelProperty("创赛号")
    private String chuangNum;

    @ApiModelProperty("分工")
    private String work;
}
