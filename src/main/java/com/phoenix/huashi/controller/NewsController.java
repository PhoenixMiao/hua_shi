package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Admin;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.AddNewsRequest;
import com.phoenix.huashi.controller.request.GetBriefNewsListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.service.NewsService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Api("新闻相关操作")
@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Admin
    @PostMapping(value = "/add", produces = "application/json")
    @ApiOperation(value = "新增新闻")
    public Result addNews(@RequestBody @NotNull AddNewsRequest request){
        try {
            return Result.success(newsService.addNews(request));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping("")
    @ApiOperation(value = "查看新闻详情")
    @ApiImplicitParam(name = "newsId", value = "新闻id", required = true, paramType = "query", dataType = "Long")
    public Result getNews(@NotNull @RequestParam("newsId") Long id){
        try {
            return Result.success(newsService.getNewsById(id));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/list", produces = "application/json")
    @ApiOperation(value = "查看简要新闻列表")
    public Result getBriefNewsList(@RequestBody @NotNull GetBriefNewsListRequest request){
        try {
            return Result.success(newsService.getBriefNewsList(request));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }


    @Admin
    @PostMapping(value = "/uploadContentRTF", produces = "application/json")
    @ApiOperation(value = "上传新闻富文本")
    public Result uploadNewsRTF(@RequestParam Long newsId, MultipartFile file){
        try {
            return Result.success(newsService.uploadContentRTF(newsId, file));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Admin
    @PostMapping(value = "/uploadPicture", produces = "application/json")
    @ApiOperation(value = "上传新闻图片")
    public Result uploadPicture(@RequestParam Long newsId, MultipartFile file){
        try {
            return Result.success(newsService.uploadPicture(newsId, file));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }
}
