package com.phoenix.huashi.mapper;

import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.entity.Notification;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {
    @Select("SELECT projectId FROM member WHERE chuangNum=#{chuangNum} AND type=#{type}")
    List<Long> getTeamByChuangNumAndMemberType(@Param("chuangNum") String chuangNum,@Param("type") String type);

    @Insert("INSERT INTO member(id,type,projectType,projectId,chuangNum,work) VALUE(null,#{type},#{projectType},#{projectId},#{chuangNum},#{work})")
    void insertMember(
            @Param("projectId") Long projectId,
            @Param("type") String type,
            @Param("projectType") Integer projectType,
            @Param("chuangNum") String chuangNum,
            @Param("work") String work);

    @Select("SELECT chuangNum,work FROM member WHERE projectId=#{projectId} AND projectType=#{projectType}")
    List<BriefMember> getMembersByProjectId(
            @Param("projectId") Long projectId,
            @Param("projectType") Integer projectType
    );
}
