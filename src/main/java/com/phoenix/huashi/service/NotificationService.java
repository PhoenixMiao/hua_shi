package com.phoenix.huashi.service;


import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;

public interface NotificationService {

    Notification getNotificationById(Long id);

    Page<BriefNotification> getBriefNotificationList(GetBriefProjectListRequest request);
}
