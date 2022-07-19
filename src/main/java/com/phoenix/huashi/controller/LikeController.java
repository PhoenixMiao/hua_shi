package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api("点赞相关操作")
@RestController
@RequestMapping("/like")
@Validated
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private SessionUtils sessionUtils;

    @Auth
    @GetMapping("")
    @ApiOperation(value = "点赞", response = String.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Result giveLike(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            likeService.like(projectId, sessionUtils.getUserChuangNum());
            return Result.success("点赞成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }


    @Auth
    @GetMapping("/cancel")
    @ApiOperation(value = "取消点赞", response = String.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Result cancelLike(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            likeService.cancelLike(projectId, sessionUtils.getUserChuangNum());
            return Result.success("取消成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }
}
