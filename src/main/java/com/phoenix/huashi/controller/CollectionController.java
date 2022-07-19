package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.service.CollectionService;

import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.phoenix.huashi.annotation.Auth;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("收藏相关操作")
@RestController
@RequestMapping("/collection")
@Validated
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Auth
    @GetMapping("")
    @ApiOperation(value = "收藏项目", response = String.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Result addToCollection(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            collectionService.addToCollection(projectId,sessionUtils.getUserChuangNum());
            return Result.success("收藏成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("/cancel")
    @ApiOperation(value = "取消收藏", response = String.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Result cancelCollection(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            collectionService.cancelCollection(projectId,sessionUtils.getUserChuangNum());
            return Result.success("取消成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/list")
    @ApiOperation(value = "查看收藏夹", response = BriefCollection.class)
    public Result getBriefCollectionList(@NotNull @Valid @RequestBody GetListRequest getListRequest) {
        try {
            return Result.success(collectionService.getBriefCollectionList(getListRequest,sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

}
