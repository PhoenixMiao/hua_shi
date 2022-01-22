package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;
import com.phoenix.huashi.entity.DisplayProject;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisplayProjectMapper extends MyMapper<DisplayProject> {
    @Select("SELECT * FROM displayProject WHERE id=#{id}")
    DisplayProject getDisplayProjectById(@Param("id")Long id);

    @Select("SELECT id,name,principal,type,status FROM displayProject ")
    List<BriefDisplayProject> getBriefDisplayProjectList();

    @Select("SELECT likes FROM displayProject WHERE id=#{id}")
    Long getLikes(@Param("id")Long id);

    @Update("UPDATE displayProject SET likes=#{likes} WHERE id=#{id};")
    void giveLike(@Param("likes")Long likes,@Param("id")Long id);

    @Select("SELECT * FROM displayProject WHERE name=#{name};")
    List<DisplayProject> searchDisplayProjectByName(@Param("name")String name);

    @Select("SELECT * FROM displayProject WHERE institute=#{institute};")
    List<DisplayProject> searchDisplayProjectByInstitute(@Param("institute")String institute);


}
