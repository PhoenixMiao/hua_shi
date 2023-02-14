package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.service.CarouselService;
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

@Api("轮播图相关操作")
@RestController
@RequestMapping("/carousel")
@Validated
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @Auth
    @PostMapping(value = "/upload", produces = "application/json;charset=UTF-8" )
    @ApiOperation(value = "上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "projectType", value = "项目类型 0招募项目 1展示项目 2通知公告", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "文件名", required = true, paramType = "query", dataType = "String")
    })
    public Result uploadFile(@NotNull @RequestParam("projectId") Long projectId,@NotNull @RequestParam("projectType") Integer projectType, @NotNull @RequestParam("name") String name, MultipartFile file) {
        try {
            return Result.success(carouselService.uploadFile(projectId,name,projectType, file));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }

    }


    @Auth
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8" )
    @ApiOperation(value = "获得轮播图列表")
    @ApiImplicitParam(name = "number", value = "轮播图数量", required = true, paramType = "query", dataType = "Integer")
    public Result getCarouselList(@NotNull @RequestParam("number") Integer number) {
        try {
            return Result.success(carouselService.getCarouselList(number));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping(value = "/allList", produces = "application/json;charset=UTF-8" )
    @ApiOperation(value = "获得全部轮播图列表")
    @ApiImplicitParam(name = "number", value = "轮播图数量", required = true, paramType = "query", dataType = "Integer")
    public Result getAllCarouselList(@NotNull @Valid @RequestBody GetListRequest getListRequest) {
        try {
            return Result.success(carouselService.getAllCarouselList(getListRequest));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

}
