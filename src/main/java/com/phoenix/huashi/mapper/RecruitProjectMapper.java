package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;

import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;

import com.phoenix.huashi.entity.RecruitProject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitProjectMapper extends MyMapper<RecruitProject> {
    @Select("SELECT * FROM recruitProject WHERE id=#{id}")
    RecruitProject getRecruitProjectById(@Param("id")Long id);

    @Select("SELECT id,name,tag1,tag2,tag3,briefDemand,status FROM recruitProject ")
    List<BriefRecruitProject> getAllBriefRList();

    @Insert("INSERT INTO recruitProject(name,captainChuangNum,captainName,institute,introduction,briefDemand,teacherName,teacherApartment,teacherRank,planStartTime,planEndTime,recruitTime,startTime,endTime,stateUpdateTime,demand,recruitNum,status,tag1,tag2,tag3) VALUE(#{name},#{captainChuangNum},#{captainName},#{institute},#{introduction},#{briefDemand},#{teacherName},#{teacherApartment},#{teacherRank},#{planStartTime},#{planEndTime},#{recruitTime},#{startTime},#{endTime},#{stateUpdateTime},#{demand},#{recruitNum},#{status},#{tag1},#{tag2},#{tag3})")
    void creatTeam(
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
    @Update("UPDATE recruitProject SET name=#{name},captainName=#{captainName},institute=#{institute},introduction=#{introduction},briefDemand=#{briefDemand},teacherName=#{teacherName},teacherApartment=#{teacherApartment},teacherRank=#{teacherRank},planStartTime=#{planStartTime},planEndTime=#{planEndTime},recruitTime=#{recruitTime},startTime=#{startTime},endTime=#{endTime},stateUpdateTime=#{stateUpdateTime},demand=#{demand},recruitNum=#{recruitNum},status=#{status} ,tag1=#{tag1},tag2=#{tag2},tag3=#{tag3}WHERE id=#{id})")
    void updateTeamById(
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
              @Param("id")Long id,
            @Param("tag1")String tag1,
            @Param("tag2")String tag2,
            @Param("tag3")String tag3
    );


}


