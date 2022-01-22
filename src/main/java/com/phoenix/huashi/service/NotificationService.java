package com.phoenix.huashi.service;


import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;

public interface NotificationService {

    Notification getNotificationById(Long id,Long user_id);
    Page<BriefNotification> getBriefNotificationList(GetBriefListRequest request);
}
