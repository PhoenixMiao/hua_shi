package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.*;

import com.phoenix.huashi.dto.recruitproject.BriefApplication;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @GetMapping("/info")
    @ApiOperation(value = "查看项目简介", response = RecruitProject.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Object getRecruitProjectById(@NotNull @RequestParam("projectId") Long id) {
        return recruitProjectService.getRecruitProjectById(id);
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取招募项目简要信息列表", response = BriefRecruitProject.class)
    public Object getBriefRecruitProjectList(@NotNull @Valid @RequestBody GetBriefProjectListRequest request) {
        return recruitProjectService.getBriefRecruitProjectList(request);
    }

    @Auth
    @PostMapping("/update")
    @ApiOperation(value = "修改当前项目团队信息", response = String.class)
    public Object updateTeamById(@NotNull @Valid @RequestBody UpdateProjectByIdRequest updateProjectByIdRequest) {
        recruitProjectService.updateProjectById(updateProjectByIdRequest);
        return "操作成功";
    }

    @Auth
    @PostMapping("/create")
    @ApiOperation(value = "创建项目", response = Long.class)
    public Object creatProject(@NotNull @Valid @RequestBody CreateProjectRequest creatTeamRequest) {
        String userChuangNum= sessionUtils.getUserChuangNum();
        return recruitProjectService.createProject(creatTeamRequest,userChuangNum);
    }

    @Auth
    @PostMapping("/updateStatus")
    @ApiOperation(value = "更新项目状态", response = String.class)
    public Object updateProjectStatusById(@NotNull @Valid @RequestBody UpdateProjectStatusRequest updateProjectStatusRequest) {
        recruitProjectService.updateProjectStatusById(updateProjectStatusRequest);
        return "操作成功";
    }

//    @Auth
//    @PostMapping("/display")
//    @ApiOperation(value = "申请成为展示项目",response = String.class)
//    public Object applyForDisplayProject(@NotNull @Valid @RequestBody ApplyForDisplayProjectRequest request){
//        recruitProjectService.applyForDisplayProject(request);
//        return "操作成功";
//    }

    @GetMapping("/applications")
    @ApiOperation(value = "获得简历数",response = Integer.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Object getApplications(@NotNull @RequestParam("projectId") Long projectId){
        return   recruitProjectService.getApplications(projectId);
    }

    @Auth
    @PostMapping("/applications/list")
    @ApiOperation(value = "申请简历列表",response = BriefApplication.class)
    public Object getBriefApplicationList(@NotNull @Valid @RequestBody GetBriefApplicationListRequest request){
        return  recruitProjectService.getBriefApplicationList(request);

    }
}
