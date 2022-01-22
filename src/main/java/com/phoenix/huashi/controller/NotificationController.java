package com.phoenix.huashi.controller;

import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notificationList")
    @ApiOperation(value = "获取通知公告简要信息列表",response = BriefNotification.class)
    public Object getBriefNotificationList(@NotNull@RequestBody GetBriefListRequest request) {
        return notificationService.getAllBriefNotificationList(request);
    }
}
