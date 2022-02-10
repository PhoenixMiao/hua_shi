package com.phoenix.huashi.controller;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.CreateProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.UpdateProjectByIdRequest;

import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("招募项目相关操作")
@RestController
@RequestMapping("/recruitProject")
@Validated
public class RecruitProjectController {
    @Autowired
    private RecruitProjectService recruitProjectService;

    @Autowired
    private SessionUtils sessionUtils;

    @GetMapping("/info/{id}")
    @ApiOperation(value = "查看项目简介", response = RecruitProject.class)
    public Object getRecruitProjectById(@PathVariable("id") Long id) {
        return recruitProjectService.getRecruitProjectById(id);
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取招募项目简要信息列表", response = BriefRecruitProject.class)
    public Object getBriefRecruitProjectList(@NotNull@Valid @RequestBody GetBriefProjectListRequest request) {
        return recruitProjectService.getBriefRecruitProjectList(request);
    }

   @Auth
    @PostMapping("/update/{id}")
    @ApiOperation(value = "修改当前项目团队信息",response = String.class)
    public Object updateTeamById(@NotNull @Valid @RequestBody UpdateProjectByIdRequest updateProjectByIdRequest){
        recruitProjectService.updateProjectById(updateProjectByIdRequest);
        return "操作成功";
    }

    @Auth
    @PostMapping("/create")
    @ApiOperation(value = "创建项目",response = String.class)
    public Object creatProject(@NotNull @Valid @RequestBody CreateProjectRequest creatTeamRequest){
        recruitProjectService.createProject(creatTeamRequest);
        return "操作成功";
    }

//    @Auth
//    @GetMapping("/display/{projectId}")
//    @ApiOperation(value = "申请成为展示项目",response = String.class)
//    public Object applyForDisplayProject(@PathVariable("projectId") Long projectId){
//        recruitProjectService.applyForDisplayProject(projectId);
//        return "操作成功";
//    }

}
