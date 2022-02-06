package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;

import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

import com.phoenix.huashi.entity.RecruitProject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitProjectMapper extends MyMapper<RecruitProject> {
    @Select("SELECT * FROM recruit_project WHERE id=#{id}")
    RecruitProject getRecruitProjectById(@Param("id")Long id);

    @Select("SELECT captain_chuang_num FROM recruit_project WHERE id=#{id}")
    String getCaptianChuangNumByProjectId(@Param("id")Long id);

    @Select("SELECT id,name,tag1,tag2,tag3,brief_demand,status FROM recruit_project ")
    List<BriefRecruitProject> getAllBriefRList();

    @Insert("INSERT INTO recruit_project(name,captain_chuang_num,captain_name,institute,introduction,brief_demand,teacher_name,teacher_apartment,teacher_rank,plan_start_time,plan_end_time,recruit_time,start_time,end_time,state_update_time,demand,recruit_num,status,tag1,tag2,tag3) VALUE(#{name},#{captainChuangNum},#{captainName},#{institute},#{introduction},#{briefDemand},#{teacherName},#{teacherApartment},#{teacherRank},#{planStartTime},#{planEndTime},#{recruitTime},#{startTime},#{endTime},#{stateUpdateTime},#{demand},#{recruitNum},#{status},#{tag1},#{tag2},#{tag3})")
    void creatProject(
            @Param("name")String name,
            @Param ("captainChuangNum")String captainChuangNum,
            @Param("captainName")String captainName,
            @Param("institute")String institute,
            @Param("introduction")String introduction,
            @Param("briefDemand")String briefDemand,
            @Param("teacherName")String teacherName,
            @Param("teacherApartment")String teacherApartment,
            @Param("teacherRank")String teacherRank,
            @Param("planStartTime")String planStartTime,
            @Param("planEndTime")String planEndTime,
            @Param("recruitTime")String recruitTime,
            @Param("startTime")String startTime,
            @Param("endTime")String endTime,
            @Param("stateUpdateTime")String stateUpdateTime,
            @Param("demand")String demand,
            @Param("status")Integer status,
            @Param("recruitNum")Long recruitNum,
            @Param("tag1")String tag1,
            @Param("tag2")String tag2,
            @Param("tag3")String tag3

    );

    @Update("UPDATE recruit_project SET name=#{name},captain_name=#{captainName},institute=#{institute},introduction=#{introduction},brief_demand=#{briefDemand},teacher_name=#{teacherName},teacher_apartment=#{teacherApartment},teacher_rank=#{teacherRank},plan_start_time=#{planStartTime},plan_end_time=#{planEndTime},recruit_time=#{recruitTime},start_time=#{startTime},end_time=#{endTime},state_update_time=#{stateUpdateTime},demand=#{demand},recruit_num=#{recruitNum},status=#{status} ,tag1=#{tag1},tag2=#{tag2},tag3=#{tag3} WHERE id=#{id}")
    void updateProjectById(
            @Param("name")String name,
            @Param("captainName")String captainName,
            @Param("institute")String institute,
            @Param("introduction")String introduction,
            @Param("briefDemand")String briefDemand,
            @Param("teacherName")String teacherName,
            @Param("teacherApartment")String teacherApartment,
            @Param("teacherRank")String teacherRank,
            @Param("planStartTime")String planStartTime,
            @Param("planEndTime")String planEndTime,
            @Param("recruitTime")String recruitTime,
            @Param("startTime")String startTime,
            @Param("endTime")String endTime,
            @Param("stateUpdateTime")String stateUpdateTime,
            @Param("demand")String demand,
            @Param("status")Integer status,
            @Param("recruitNum")Long recruitNum,
            @Param("tag1")String tag1,
            @Param("tag2")String tag2,
            @Param("tag3")String tag3,
            @Param("id")Long id
    );


}


