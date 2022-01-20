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
@ApiModel("Member 成员")
public class Member  implements Serializable{
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("类别")
    private String type;

    @ApiModelProperty("招募项目id")
    private Long recruitProjectId;

    @ApiModelProperty("创赛号")
    private String chuangNum;


}
