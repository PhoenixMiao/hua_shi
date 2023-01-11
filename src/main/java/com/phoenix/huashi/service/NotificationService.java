package com.phoenix.huashi.service;


import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification getNotificationById(Long id);

    List<BriefNotification> getHomepageNotification();

    Page<BriefNotification> getBriefNotificationList(GetBriefProjectListRequest request);

    Page<BriefNotification> searchNotification(SearchRequest searchRequest);
}
