package com.phoenix.huashi.controller;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;

import com.phoenix.huashi.service.DisplayProjectService;

import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
public class DisplayProjectController {
    @Autowired
    private DisplayProjectService displayProjectService;

    @Autowired
    private SessionUtils sessionUtils;

    @GetMapping("/displayProject/{id}")
    @ApiOperation(value = "查看项目详情", response = DisplayProject.class)
    public Object getDisplayProjectById(@PathVariable("id") Long displayProjectId) {
        Long userId = sessionUtils.getUserId();
        return displayProjectService.getDisplayProjectById(displayProjectId, userId);
    }
    @PostMapping("/displayProjectList")
    @ApiOperation(value = "获取展示项目简要信息列表", response = BriefDisplayProject.class)
    public Object getBriefDisplayProjectList(@NotNull@Valid @RequestBody GetBriefListRequest request) {
        return displayProjectService.getBriefDisplayProjectList(request);
    }
//    @Auth
    @GetMapping("/like/{projectId}")
    @ApiOperation(value = "点赞",response = String.class)
    public Object giveLike(@NotBlank @PathVariable("projectId") Long projectId){
        displayProjectService.giveLike(projectId);
        return "操作成功";
    }
    @GetMapping("/getLike/{projectId}")
    @ApiOperation(value = "获取项目点赞数",response = Long.class)
    public Object getLike(@NotBlank @PathVariable("projectId") Long projectId){
        Long likes=displayProjectService.getLike(projectId);
        return likes;
    }

}
