package com.phoenix.huashi.mapper;

import com.phoenix.huashi.enums.MessageTypeEnum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageMapper {
    @Insert("INSERT INTO message(type,projectId,memberChuangNum,status,statusUpdateTime,reason,isRead) VALUE(#{type},#{projectId},#{memberChuangNum},#{status},#{statusUpdateTime},#{reason},#{isRead})")
    void joinProject(
            @Param("type") String type,
            @Param ("projectId")Long projectId,
            @Param("memberChuangNum")String memberChuangNum,
            @Param("status")Integer status,
            @Param("statusUpdateTime")String statusUpdateTime,
            @Param("reason")String reason,
            @Param("isRead")Integer isRead);
}
