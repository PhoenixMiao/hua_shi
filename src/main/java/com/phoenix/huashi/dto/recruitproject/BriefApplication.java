package com.phoenix.huashi.dto.recruitproject;
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
@ApiModel("BriefApplication 简历简要信息")
public class BriefApplication {
    @ApiModelProperty("用户创赛号")
    private String memberChuangNum;

    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("年级")
    private String grade;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("QQ号")
    private String QQ;

    @ApiModelProperty("是否已读")
    private Integer isRead;

    @ApiModelProperty("状态更新时间")
    private String statusUpdateTime;

    @ApiModelProperty("状态 0表示未处理 1/-1表示已处理")
    private Integer status;
}
