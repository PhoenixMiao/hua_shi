package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetRecruitProjectResponse;
import com.phoenix.huashi.dto.recruitproject.BriefApplication;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecruitProjectService {
    GetRecruitProjectResponse getRecruitProjectById(Long id);

    Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefProjectListRequest request);

    List<BriefRecruitProject> getHomepageBriefRecruitProjectList();

    List<BriefRecruitProject> getHomepageMyProjectList(String userChuangNum);

    Long createProject(CreateProjectRequest creatTeamRequest, String userChuangNum);

    void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest);

    void applyForDisplayProject(ApplyForDisplayProjectRequest request);

    void updateProjectStatusById(UpdateProjectStatusRequest updateProjectStatusRequest);

    Integer getApplications(Long projectId);

    Page<BriefApplication> getBriefApplicationList(GetBriefApplicationListRequest request);

    Page<BriefRecruitProject> searchRecruitProject(SearchRequest searchRequest);

    String uploadDemandRTF(MultipartFile multipartFile);

    String uploadIntroductionRTF(MultipartFile multipartFile);

    void updateDemandRTF(Long recruitProjectId, String demand);

    void updateIntroductionRTF(Long recruitProjectId, String introduction);
}
