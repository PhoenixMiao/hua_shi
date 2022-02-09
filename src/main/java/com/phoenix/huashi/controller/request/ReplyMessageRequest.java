package com.phoenix.huashi.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("ReplyMessageRequest 处理消息")
public class ReplyMessageRequest {
    @ApiModelProperty("消息id")
    private Long id;

    @ApiModelProperty("状态：ACCEPT接受，REFUSE拒绝")
    private String status;

    @ApiModelProperty("理由：接受时不填写该字段")
    private String reason;
}
