package com.phoenix.huashi.controller;

import com.phoenix.huashi.controller.Request.GetBriefListRequest;
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



    @GetMapping("/{id}")
    @ApiOperation(value = "查看公告详情", response = Notification.class)
    public Object getNotificatinById(@PathVariable("id") Long notificationId) {
        Long user_id = sessionUtils.getUserId();
        return notificationService.getNotificationById(notificationId, user_id);
    }

    @PostMapping("/notificationList")
    @ApiOperation(value = "获取通知公告简要信息列表", response = BriefNotification.class)
    public Object getBriefNotificationList(@NotNull@Valid @RequestBody GetBriefListRequest request) {
        return notificationService.getBriefNotificationList(request);
}}
