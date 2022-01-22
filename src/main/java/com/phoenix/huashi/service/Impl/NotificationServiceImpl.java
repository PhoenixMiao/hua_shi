package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.mapper.NotificationMapper;
import com.phoenix.huashi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.phoenix.huashi.enums.CommodityTypeEnum;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;


    @Override
    public Notification getNotificationById (Long id, Long user_id){
        Notification notification = notificationMapper.getNotificationById(id);

        return notification;
    }

    @Override
    public Page<BriefNotification> getBriefNotificationList(@NotNull @RequestBody GetBriefListRequest request)
    { if(request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize(),pageParam.getOrderBy());

        if(request.getType().equals(CommodityTypeEnum.INNOVATIONCOMPETITION.getName())){
            List<BriefNotification> briefNotificationList = notificationMapper.getBriefNotificationList(CommodityTypeEnum.INNOVATIONCOMPETITION.getName());
            return new Page(new PageInfo<>(briefNotificationList));
        }else if(request.getType().equals(CommodityTypeEnum.ACADEMICCOMPITITION.getName())) {
            List<BriefNotification> briefNotificationList = notificationMapper.getBriefNotificationList(CommodityTypeEnum.ACADEMICCOMPITITION.getName());
            return new Page(new PageInfo<>(briefNotificationList));
        }

        return null;

}}


