package com.phoenix.huashi.service;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;


import com.phoenix.huashi.entity.DisplayProject;

public interface DisplayProjectService {
    void giveLike(Long projectId,String userChuangNum);
    Long getLike(Long projectId);
    DisplayProject getDisplayProjectById(Long id);
    Page<BriefDisplayProject> getBriefDisplayProjectList(GetBriefProjectListRequest request);

}
