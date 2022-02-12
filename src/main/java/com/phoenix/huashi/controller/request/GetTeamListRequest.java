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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("GetTeamListRequest 获取组队列表请求")
public class GetTeamListRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    private PageParam pageParam;

    @ApiModelProperty("队伍类型 0我加入的 1我负责的 2全部")
    private Integer teamType;
}
