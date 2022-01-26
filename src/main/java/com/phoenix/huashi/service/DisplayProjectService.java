package com.phoenix.huashi.service;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;


import com.phoenix.huashi.entity.DisplayProject;

public interface DisplayProjectService {
    void giveLike(Long projectId,Long userId);

    DisplayProject getDisplayProjectById(Long id, Long userId);
    Page<BriefDisplayProject> getBriefDisplayProjectList(GetBriefListRequest request);

}
