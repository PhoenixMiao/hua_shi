package com.phoenix.huashi.service;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.CreatTeamRequest;
import com.phoenix.huashi.controller.request.GetBriefListRequest;
import com.phoenix.huashi.controller.request.UpdateTeamByIdRequest;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.entity.RecruitProject;

public interface RecruitProjectService {
    RecruitProject getRecruitProjectById(Long id);
    Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefListRequest request);
    void creatTeam(CreatTeamRequest creatTeamRequest, Long userId);
    void updateTeamById(UpdateTeamByIdRequest updateTeamByIdRequest,Long id);
}
