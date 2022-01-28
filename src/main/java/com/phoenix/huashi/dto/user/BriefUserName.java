package com.phoenix.huashi.dto.user;
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
@ApiModel("BriefUserName 用户姓名简要信息")
public class BriefUserName {
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("创赛号")
    private String chuangNum;
}
