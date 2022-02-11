package com.phoenix.huashi.controller.response;

import com.phoenix.huashi.dto.user.RecruitProjectMember;
import com.phoenix.huashi.entity.RecruitProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("GetRecruitProjectResponse 获取招募项目详细信息")
public class GetRecruitProjectResponse {
    @ApiModelProperty("项目信息")
    private RecruitProject recruitProject;


    @ApiModelProperty("已有成员信息")
    private List<RecruitProjectMember> members;


}
