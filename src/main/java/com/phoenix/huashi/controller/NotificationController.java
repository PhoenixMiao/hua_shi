package com.phoenix.huashi.controller;

import com.phoenix.huashi.controller.request.GetBriefListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SessionUtils sessionUtils;



    @GetMapping("/notification/{id}")
    @ApiOperation(value = "查看公告详情", response = Notification.class)
    public Object getNotificationById(@PathVariable("id") Long notificationId) {
        return notificationService.getNotificationById(notificationId);
    }

    @PostMapping("/notificationList")
    @ApiOperation(value = "获取通知公告简要信息列表", response = BriefNotification.class)
    public Object getBriefNotificationList(@NotNull@Valid @RequestBody GetBriefListRequest request) {
        return notificationService.getBriefNotificationList(request);
}}
