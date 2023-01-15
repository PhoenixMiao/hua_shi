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
@ApiModel("GetBriefRecruitProjectListRequest 获取招募项目简要信息列表")
public class GetBriefRecruitProjectListRequest {
    @NotNull
    @ApiModelProperty("分页参数")
    PageParam pageParam;

    @ApiModelProperty("项目类型 0为学生招募，1为教师招募 若不填则不区分")
    Integer type;
}
