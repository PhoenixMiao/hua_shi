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


}
