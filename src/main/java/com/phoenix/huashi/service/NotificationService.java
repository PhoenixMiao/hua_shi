package com.phoenix.huashi.service;


import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;

public interface NotificationService {
    Page<BriefNotification> getAllBriefNotificationList(GetBriefListRequest request);
}
