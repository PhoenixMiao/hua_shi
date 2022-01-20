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
@ApiModel("Collection 收藏")
public class Collection  implements Serializable{
    @Id
    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("展示项目id")
    private Long recruitProjectId;

    @ApiModelProperty("用户创赛号")
    private String chuangNum;

    @ApiModelProperty("收藏时间")
    private String collectTime;
}
