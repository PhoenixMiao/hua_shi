package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.*;

import com.phoenix.huashi.dto.recruitproject.BriefApplication;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "query", dataType = "Long")
    public Result getRecruitProjectById(@NotNull @RequestParam("projectId") Long id) {
        try {
            return Result.success(recruitProjectService.getRecruitProjectById(id));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }

    }

    @PostMapping("/list")
    @ApiOperation(value = "获取招募项目简要信息列表", response = BriefRecruitProject.class)
    public Result getBriefRecruitProjectList(@NotNull @Valid @RequestBody GetBriefProjectListRequest request) {
        try {
            return Result.success(recruitProjectService.getBriefRecruitProjectList(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/update")
    @ApiOperation(value = "修改当前项目团队信息", response = String.class)
    public Object updateTeamById(@NotNull @Valid @RequestBody UpdateProjectByIdRequest updateProjectByIdRequest) {
        try {
            recruitProjectService.updateProjectById(updateProjectByIdRequest);
            return Result.success("修改成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/create")
    @ApiOperation(value = "创建项目", response = Long.class)
    public Object creatProject(@NotNull @Valid @RequestBody CreateProjectRequest creatTeamRequest) {
        try {
            return Result.success(recruitProjectService.createProject(creatTeamRequest, sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/updateStatus")
    @ApiOperation(value = "更新项目状态", response = String.class)
    public Result updateProjectStatusById(@NotNull @Valid @RequestBody UpdateProjectStatusRequest updateProjectStatusRequest) {
        try {
            recruitProjectService.updateProjectStatusById(updateProjectStatusRequest);
            return Result.success("更新成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/display")
    @ApiOperation(value = "申请成为展示项目",response = String.class)
    public Result applyForDisplayProject(@NotNull @Valid @RequestBody ApplyForDisplayProjectRequest request){
        try {
            recruitProjectService.applyForDisplayProject(request);
            return Result.success("申请成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping("/applications")
    @ApiOperation(value = "获得简历数", response = Integer.class)
    @ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "query", dataType = "Long")
    public Result getApplications(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            return Result.success(recruitProjectService.getApplications(projectId));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/applications/list")
    @ApiOperation(value = "申请简历列表", response = BriefApplication.class)
    public Object getBriefApplicationList(@NotNull @Valid @RequestBody GetBriefApplicationListRequest request) {
        try {
            return Result.success(recruitProjectService.getBriefApplicationList(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/introductionUpload", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "上传介绍富文本")

    public Result uploadIntroduction( MultipartFile file) {
        try {
            return Result.success(recruitProjectService.uploadIntroductionRTF( file));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }

    }
    @PostMapping(value = "/demandUpload", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "上传要求富文本文件")
    public Result uploadDemand( MultipartFile file) {
        try {
            return Result.success(recruitProjectService.uploadDemandRTF( file));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }

    }


    @Auth
    @PostMapping("/updateIntroduction")
    @ApiOperation(value = "更新当前招募项目介绍富文本", response = String.class)
    @ApiImplicitParams({ @ApiImplicitParam(name="introduction",value="介绍富文本",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "recruitProjectId", value = "招募项目id", required = true, paramType = "query", dataType = "Long")})
    public Result updateResume(@NotNull @RequestParam("introduction") String introduction,@NotNull @RequestParam("recruitProjectId") Long recruitProjectId) {
        try {
            recruitProjectService.updateIntroductionRTF(recruitProjectId,introduction);
            return  Result.success("更新成功");
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/updateDemand")
    @ApiOperation(value = "更新当前招募项目要求富文本", response = String.class)
    @ApiImplicitParams({ @ApiImplicitParam(name="demand",value="要求富文本",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "recruitProjectId", value = "招募项目id", required = true, paramType = "query", dataType = "Long")})
    public Result updateDemand(@NotNull @RequestParam("demand") String demand,@NotNull @RequestParam("recruitProjectId") Long recruitProjectId) {
        try {
            recruitProjectService.updateIntroductionRTF(recruitProjectId,demand);
            return  Result.success("更新成功");
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }
}
