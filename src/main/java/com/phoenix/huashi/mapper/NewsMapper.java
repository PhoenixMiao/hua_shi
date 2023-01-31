package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper extends MyMapper<News> {
    @Select("SELECT * FROM notification WHERE id=#{id}")
    News getNewsById(@Param("id") Long id);

    @Select("SELECT id,title,publish_date FROM news")
    List<News> getALLBriefNotificationList();
}
