package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("公告相关操作")
@RestController
@RequestMapping("/notification")
@Validated
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SessionUtils sessionUtils;


    @GetMapping("")
    @ApiOperation(value = "查看公告详情", response = Notification.class)
    @ApiImplicitParam(name="notificationId",value="公告id",required = true,paramType = "query",dataType = "Long")
    public Result getNotificationById(@NotNull @RequestParam("notificationId") Long notificationId) {
        try {
       return Result.success(notificationService.getNotificationById(notificationId));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }

    }

    @PostMapping("/list")
    @ApiOperation(value = "获取通知公告简要信息列表", response = BriefNotification.class)
    public Result getBriefNotificationList(@NotNull @Valid @RequestBody GetBriefProjectListRequest request) {
        try {
            return Result.success(notificationService.getBriefNotificationList(request));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }
}
