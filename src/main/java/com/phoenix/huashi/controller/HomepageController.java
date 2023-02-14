package com.phoenix.huashi.controller;
import com.phoenix.huashi.annotation.Admin;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;
import com.phoenix.huashi.dto.displayproject.BriefDisplayVoteProject;
import com.phoenix.huashi.dto.displayproject.BriefHomepageDisplayProject;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("首页相关操作")
@RestController
@RequestMapping("/homepage")
@Validated
public class HomepageController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RecruitProjectService recruitProjectService;

    @Autowired
    private DisplayProjectService displayProjectService;


    @Autowired
    private SessionUtils sessionUtils;


    @Auth
    @GetMapping("/notification")
    @ApiOperation(value = "获取首页通知公告简要信息列表", response = BriefNotification.class)
    public Result getHomepageNotificationList() {
        try {
            return Result.success(notificationService.getHomepageNotification());
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("/recruitProject")
    @ApiOperation(value = "获取首页招募项目简要信息列表", response = BriefRecruitProject.class)
    public Result getHomepageRList() {
        try {
            return Result.success(recruitProjectService.getHomepageBriefRecruitProjectList());
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("/displayProject")
    @ApiOperation(value = "获取首页展示项目简要信息列表", response = BriefHomepageDisplayProject.class)
    @ApiImplicitParam(name="year",value="项目年份",required = true,paramType = "query",dataType = "String")
    public Result getHomepageDList(String year) {
        try {
            return Result.success(displayProjectService.getHomePageDisplayProjectList(year));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("/project")
    @ApiOperation(value = "获取首页项目管理简要信息列表", response = BriefHomepageDisplayProject.class)
    public Result getHomepagePList() {
        try {
            return Result.success(recruitProjectService.getHomepageMyProjectList(sessionUtils.getUserChuangNum()));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }
}
