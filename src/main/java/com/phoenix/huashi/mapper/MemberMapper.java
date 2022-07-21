package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.dto.member.Experience;
import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.Notification;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper extends MyMapper<Member> {
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

    @Select("SELECT * FROM member WHERE project_id=#{project_id} AND chuang_num=#{chuang_num}")
    Member getMemberByProjectIdAndChuangNum(
            @Param("project_id") Long projectId,
            @Param("chuang_num") String chuang_num
    );

    @Update("UPDATE member SET work=#{work} WHERE project_id=#{project_id} AND chuang_num=#{chuang_num}")
    void updateMemberWork(
            @Param("project_id") Long projectId,
            @Param("chuang_num") String chuang_num,
            @Param("work") String work
    );

    @Select("SELECT * FROM recruit_project JOIN (SELECT * FROM member WHERE member.chuang_num=#{chuang_num} ) tmp on tmp.project_id=recruit_project.id WHERE status=#{status}")
    List<Experience> getMemberExperienceInRecruitProject(
            @Param("chuang_num") String chuang_num,
            @Param("status") Integer status
    );

    @Select("SELECT * FROM display_project JOIN (SELECT * FROM member WHERE member.chuang_num=#{chuang_num} ) tmp ON tmp.project_id=display_project.id")
    List<Experience> getMemberExperienceInDisplayProject(
            @Param("chuang_num") String chuang_num
    );
}
