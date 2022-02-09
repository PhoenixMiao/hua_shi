package com.phoenix.huashi.dto.Message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BriefMessage 简要消息显示")
public class BriefMessage {
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("type")
    private String type;

    @ApiModelProperty("相关项目id")
    private Long projectId;

    @ApiModelProperty("预加入成员创赛号")
    private String memberChuangNum;

    @ApiModelProperty("预加入成员昵称")
    private String memberNickname;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("是否已读")
    private String isRead;

    @ApiModelProperty("项目负责人创赛号")
    private String projectPrincipalChuangNum;

    @ApiModelProperty("项目负责人昵称")
    private String projectPrincipalNickname;
}
