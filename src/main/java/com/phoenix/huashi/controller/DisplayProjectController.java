package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;

import com.phoenix.huashi.service.DisplayProjectService;

import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api("展示项目相关操作")
@RestController
@RequestMapping("/displayProject")
@Validated
public class DisplayProjectController {
    @Autowired
    private DisplayProjectService displayProjectService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private LikeService likeService;

    @GetMapping("/{id}")
    @ApiOperation(value = "查看项目详情", response = DisplayProject.class)
    public Object getDisplayProjectById(@PathVariable("id") Long displayProjectId) {
        return displayProjectService.getDisplayProjectById(displayProjectId);
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取展示项目简要信息列表", response = BriefDisplayProject.class)
    public Object getBriefDisplayProjectList(@NotNull @Valid @RequestBody GetBriefProjectListRequest request) {
        return displayProjectService.getBriefDisplayProjectList(request);
    }


}
