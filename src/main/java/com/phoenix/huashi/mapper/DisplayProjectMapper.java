package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;
import com.phoenix.huashi.entity.DisplayProject;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DisplayProjectMapper extends MyMapper<DisplayProject> {
    @Select("SELECT * FROM displayproject WHERE id=#{id}")
    DisplayProject getDisplayProjectById(@Param("id")Long id);

    @Select("SELECT id,name,principal,type,status FROM displayproject WHERE id=#{id}")
    List<BriefDisplayProject> getBriefNotificationList(@Param("id")Long id);

    @Select("SELECT likes FROM displayproject WHERE id=#{id}")
    int getLikes(@Param("id")Long id);

    @Update("UPDATE displayproject SET likes=#{likes} WHERE id=#{id};")
    void giveLike(@Param("likes")Long likes,@Param("id")Long id);

    @Select("SELECT * FROM displayproject WHERE name=#{name};")
    List<DisplayProject> searchDisplayProjectByName(@Param("name")String name);

    @Select("SELECT * FROM displayproject WHERE institute=#{institute};")
    List<DisplayProject> searchDisplayProjectByInstitute(@Param("institute")String institute);


}
