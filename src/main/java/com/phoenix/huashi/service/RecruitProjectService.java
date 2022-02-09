package com.phoenix.huashi.service;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.CreateProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.UpdateProjectByIdRequest;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.entity.RecruitProject;

public interface RecruitProjectService {
    RecruitProject getRecruitProjectById(Long id);
    Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefProjectListRequest request);
    void createProject(CreateProjectRequest creatTeamRequest);
    void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest);
//    void applyForDisplayProject(Long projectId);
}
