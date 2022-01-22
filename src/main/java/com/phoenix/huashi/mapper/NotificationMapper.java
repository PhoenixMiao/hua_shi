package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NotificationMapper extends  MyMapper<Notification>{
    @Select("SELECT * FROM notification WHERE id=#{id}")
    Notification getNotificationById(@Param("id")Long id);

    @Select("SELECT id,titile,source,publishDate FROM notification WHERE type=#{type}")
    List<BriefNotification> getBriefNotificationList(@Param("type")String type);

}
