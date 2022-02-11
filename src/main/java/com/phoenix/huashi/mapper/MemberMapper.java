package com.phoenix.huashi.mapper;

import com.phoenix.huashi.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {
    @Select("SELECT recruitProjectId FROM member WHERE chuangNum=#{chuangNum}")
    List<Long> getTeamByChuangNum(@Param("chuangNum") String chuangNum);

    @Insert("INSERT INTO member VALUE(null,#{type},#{projectId},#{chuangNum})")
    void insertMember(
            @Param("projectId") Long projectId,
            @Param("type") String type,
            @Param("chuangNum") String chuangNum);
}
