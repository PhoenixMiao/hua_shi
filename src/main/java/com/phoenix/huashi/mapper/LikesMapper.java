package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.Collection;
import com.phoenix.huashi.entity.Likes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface LikesMapper extends MyMapper<Likes> {


    @Insert("INSERT INTO likes VALUE(null,#{projectId},#{chuangNum},#{likeTime})")
    void addToLikes(
            @Param("projectId")Long projectId,
            @Param ("chuangNum")String chuangNum,
            @Param("likeTime")String likeTime);



}


