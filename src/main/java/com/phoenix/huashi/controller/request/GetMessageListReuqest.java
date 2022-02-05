package com.phoenix.huashi.controller.request;

import com.phoenix.huashi.common.PageParam;
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
@ApiModel("GetMessageListReuqest 获取消息列表请求")
public class GetMessageListReuqest {
    @NotNull
    @ApiModelProperty("分页参数")
    private PageParam pageParam;
    @ApiModelProperty("列表类型（ALL为全部；RECIEVE为我收到的；SEND为我发送的；APPLICATION为申请；INVITATION为邀请）")
    private String type;
}
