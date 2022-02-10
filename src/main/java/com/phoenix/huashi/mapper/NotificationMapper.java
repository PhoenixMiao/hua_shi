package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMapper extends  MyMapper<Notification>{
    @Select("SELECT * FROM notification WHERE id=#{id}")
    Notification getNotificationById(@Param("id")Long id);

    @Select("SELECT id,title,source,publish_date FROM notification WHERE type=#{type}")
     List<BriefNotification> getBriefNotificationList(@Param("type") String type);


    @Select("SELECT id,title,source,publish_date FROM notification ")
    List<BriefNotification> getALLBriefNotificationList();
}
