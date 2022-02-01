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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("GetBriefUserNameListRequest 根据姓名获取用户姓名、创赛号列表")
public class GetBriefUserNameListRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    PageParam pageParam;


    @ApiModelProperty("姓名")
    String name;
}
