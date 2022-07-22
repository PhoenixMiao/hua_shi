package com.phoenix.huashi.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;

import com.phoenix.huashi.service.DisplayProjectService;

import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @GetMapping("")
    @ApiOperation(value = "查看项目详情", response = DisplayProject.class)
    @ApiImplicitParam(name = "projectId", value = "展示项目id", required = true, paramType = "query", dataType = "Long")
    public Result getDisplayProjectById(@NotNull @RequestParam("projectId") Long displayProjectId) {
        try {
            return Result.success(displayProjectService.getDisplayProjectById(displayProjectId));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("likeOrCollect")
    @ApiOperation(value = "查看用户是否点赞/收藏过此项目 0表示未点赞未收藏 1表示点赞未收藏 2表示收藏未点赞 3表示收藏点赞", response = Integer.class)
    @ApiImplicitParam(name = "projectId", value = "展示项目id", required = true, paramType = "query", dataType = "Long")
    public Result judgeLikeOrCollect(@NotNull @RequestParam("projectId") Long displayProjectId) {
        try {
            return Result.success(displayProjectService.judgeLikeOrCollect(displayProjectId,sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取展示项目简要信息列表", response = BriefDisplayProject.class)
    public Result getBriefDisplayProjectList(@NotNull @Valid @RequestBody GetBriefProjectListRequest request) {
        try {
            return Result.success(displayProjectService.getBriefDisplayProjectList(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/add")
    @ApiOperation(value = "增加展示项目", response = String.class)
    public Result applyForDisplayProject(@NotNull @Valid @RequestBody ApplyForDisplayProjectRequest request) {
        try {
            return Result.success(displayProjectService.addDisplayProject(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

@Auth
    @PostMapping(value = "/upload", produces = "application/json")
    @ApiOperation(value = "上传文件")
    @ApiImplicitParam(name = "projectId", value = "展示项目id", required = true, paramType = "query", dataType = "Long")
    public Result uploadFile(@NotNull @RequestParam("projectId") Long displayProjectId, MultipartFile file, HttpServletRequest request) throws UnsupportedEncodingException {
    request.setCharacterEncoding("utf-8"); //设置编码

    MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

    //对应前端的upload的name参数"file"
    MultipartFile multipartFile = req.getFile("file");
    try {
        return Result.success(displayProjectService.uploadFile(displayProjectId,multipartFile,request));
    } catch (CommonException e) {
        return Result.result(e.getCommonErrorCode());
    }
    }

    @GetMapping(value = "/downloadResume/{flag}",produces = "application/json")
    @ApiOperation(value = "下载附件（pdf或markdown）,整个链接upload接口曾经给过")
    public Result downloadResume(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        if(fileName.equals("")) return Result.result(CommonErrorCode.FILE_NOT_EXIST);
        try{
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            return Result.result(CommonErrorCode.DOWNLOAD_FILE_FAILED);
        }
        return Result.success("下载成功");
    }

}

