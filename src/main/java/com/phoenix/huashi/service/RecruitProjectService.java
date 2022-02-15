package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetRecruitProjectResponse;
import com.phoenix.huashi.dto.recruitproject.BriefApplication;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

public interface RecruitProjectService {
    GetRecruitProjectResponse getRecruitProjectById(Long id);

    Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefProjectListRequest request);

    Long createProject(CreateProjectRequest creatTeamRequest);

    void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest);

    void applyForDisplayProject(ApplyForDisplayProjectRequest request);

    void finishTeamById(Long id);

    Integer getApplications(Long projectId);

    Page<BriefApplication> getBriefApplicationList(GetBriefApplicationListRequest request);
}
