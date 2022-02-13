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
@ApiModel("GetApplicationListRequest 获取简要信息列表")
public class GetApplicationsRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    PageParam pageParam;

    @NotNull
    @ApiModelProperty("项目id")
    Long id;

}