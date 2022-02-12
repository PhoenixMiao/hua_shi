package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.controller.request.CreateProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.UpdateProjectByIdRequest;
import com.phoenix.huashi.controller.response.GetRecruitProjectResponse;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.entity.RecruitProject;

public interface RecruitProjectService {
    GetRecruitProjectResponse getRecruitProjectById(Long id);

    Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefProjectListRequest request);

    Long createProject(CreateProjectRequest creatTeamRequest);

    void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest);

    void applyForDisplayProject(ApplyForDisplayProjectRequest request);
}
