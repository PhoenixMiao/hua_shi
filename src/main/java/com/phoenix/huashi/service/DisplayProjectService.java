package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.GetDisplayProjectResponse;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;


import com.phoenix.huashi.entity.DisplayProject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DisplayProjectService {
    DisplayProject getDisplayProjectById(Long id);

    Page<BriefDisplayProject> getBriefDisplayProjectList(GetBriefProjectListRequest request);

    Page<BriefDisplayProject> searchDisplayProject(SearchRequest searchRequest);

    Long addDisplayProject(ApplyForDisplayProjectRequest applyForDisplayProjectRequest);

    Integer judgeLikeOrCollect(Long displayProjectId, String userChuangNum);

    String uploadFile(Long displayProjectId, String fileName, Integer projectType, MultipartFile multipartFile);

    String fileDelete(String url, Long displayProjectId);

    List<DisplayProject> getDisplayProjectUncheckedList();

    String updateDisplayProjectStatus(Integer newStatus, Long displayProjectId);

    Page<DisplayProject> getAllDisplayProjectList(GetListRequest getListRequest);
}
