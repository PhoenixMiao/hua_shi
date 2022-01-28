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
@ApiModel("Message 消息")

public class Message  implements Serializable{
    @Id
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("类别")
    private String type;

    @ApiModelProperty("相关项目id")
    private Long projectId;

    @ApiModelProperty("预加入成员创赛号")
    private String memberChuangNum;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("状态更新时间")
    private String statusUpdateTime;

    @ApiModelProperty("拒绝理由")
    private String reason;

    @ApiModelProperty("是否已读")
    private String isRead;
}
