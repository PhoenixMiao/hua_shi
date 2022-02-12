package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;
import com.phoenix.huashi.entity.DisplayProject;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisplayProjectMapper extends MyMapper<DisplayProject> {
    @Select("SELECT * FROM displayProject WHERE id=#{id}")
    DisplayProject getDisplayProjectById(@Param("id") Long id);

    @Select("SELECT * FROM displayProject ")
    List<DisplayProject> getBriefDisplayProjectList();

    @Select("SELECT * FROM displayProject WHERE type=#{type}")
    List<DisplayProject> getBriefDisplayProjectListByType(@Param("type") String type);

    @Select("SELECT likes FROM displayProject WHERE id=#{id}")
    Long getLikes(@Param("id") Long id);

    @Select("SELECT collections FROM displayProject WHERE id=#{id}")
    Long getCollections(@Param("id") Long id);

    @Update("UPDATE displayProject SET likes=#{likes} WHERE id=#{id};")
    void giveLike(@Param("likes") Long likes, @Param("id") Long id);

    @Update("UPDATE displayProject SET collections=#{collections} WHERE id=#{id};")
    void collect(@Param("collections") Long collections, @Param("id") Long id);

    @Insert("INSERT INTO displayProject(id,year,institute,name,captainChuangNum,captainName,teacherName,teacherApartment,teacherRank,teacherStudy,uploadTime,introduction,type,major,number,award,innovation,likes,paper,deadline,personLimit,collections) VALUE(null,#{year},#{institute},#{name},#{captainChuangNum},#{captainName},#{teacherName},#{teacherApartment},#{teacherRank},#{teacherStudy},#{uploadTime},#{introduction},#{type},#{major},#{number},#{award},#{innovation},#{likes},#{paper},#{deadline},#{personLimit},#{collections})")
    void addToDisplayProject(
            @Param("year") String year,
            @Param("projectId") Long projectId,
            @Param("institute") String institute,
            @Param("name") String name,
            @Param("captainName") String captainName,
            @Param("captainChuangNum") String captainChuangNum,
            @Param("teacherName") String teacherName,
            @Param("teacherApartment") String teacherApartment,
            @Param("teacherRank") String teacherRank,
            @Param("teacherStudy") String teacherStudy,
            @Param("uploadTime") String uploadTime,
            @Param("introduction") String introduction,
            @Param("type") String type,
            @Param("major") String major,
            @Param("number") String number,
            @Param("award") String award,
            @Param("likes") Long likes,
            @Param("paper") String paper,
            @Param("deadline") String deadline,
            @Param("personLimit") Long personLimit,
            @Param("collections") Long collections
            );

    @Insert("INSERT INTO displayProject(id,year,institute,name,captainChuangNum,captainName,teacherName,teacherApartment,teacherRank,teacherStudy,uploadTime,introduction,type,major,number,award,innovation,likes,paper,deadline,personLimit,collections)  VALUE(null,#{year},#{institute},#{name},#{captainChuangNum},#{captainName},#{teacherName},#{teacherApartment},#{teacherRank},#{teacherStudy},#{uploadTime},#{introduction},#{type},#{major},#{number},#{award},#{innovation},#{likes},#{paper},#{deadline},#{personLimit},#{collections})")
    void newDisplayProject(ApplyForDisplayProjectRequest displayProject);
}
