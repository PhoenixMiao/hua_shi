package com.phoenix.huashi.controller.Request;
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
@ApiModel("GetCollectionRequest 查看收藏夹")
public class GetCollectionRequest {
    @NotNull
    @ApiModelProperty("分页参数")
     PageParam pageParam;

}
