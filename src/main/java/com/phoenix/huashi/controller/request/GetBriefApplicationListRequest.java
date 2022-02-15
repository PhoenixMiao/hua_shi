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
@ApiModel("GetBriefApplicationListRequest 获取简要简历列表")
public class GetBriefApplicationListRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    PageParam pageParam;

    @ApiModelProperty("项目")
    Long id ;
}
