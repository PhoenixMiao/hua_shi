package com.phoenix.huashi.controller.Request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("AddToCollection 收藏项目")
public class AddToCollectionRequest {
    @NotNull
    @ApiModelProperty(" 展示项目id")
     Long recruitProjectId;
}
