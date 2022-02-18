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
    @Select("SELECT project_id FROM member WHERE chuang_num=#{chuang_num} AND type=#{type}")
    List<Long> getTeamByChuangNumAndMemberType(@Param("chuang_num") String chuangNum,@Param("type") String type);

    @Select("SELECT project_id FROM member WHERE chuang_num=#{chuang_num}")
    List<Long> getTeamByChuangNum(@Param("chuang_num") String chuangNum);

    @Insert("INSERT INTO member(id,type,project_type,project_id,chuang_num,work) VALUE(null,#{type},#{project_type},#{project_id},#{chuang_num},#{work})")
    void insertMember(
            @Param("project_id") Long projectId,
            @Param("type") String type,
            @Param("project_type") Integer projectType,
            @Param("chuang_num") String chuangNum,
            @Param("work") String work);

    @Select("SELECT chuang_num,work FROM member WHERE project_id=#{project_id} AND project_type=#{project_type}")
    List<BriefMember> getMembersByProjectId(
            @Param("project_id") Long projectId,
            @Param("project_type") Integer projectType
    );
}
