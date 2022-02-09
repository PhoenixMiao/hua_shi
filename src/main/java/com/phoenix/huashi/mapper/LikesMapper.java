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

@Repository
public interface LikesMapper extends MyMapper<Likes> {


    @Insert("INSERT INTO likes VALUE(null,#{projectId},#{userId},#{likeTime})")
    void addToLikes(
            @Param("projectId")Long projectId,
            @Param ("userId")String  userChuangNum,
            @Param("likeTime")String likeTime);
    @Select("SELECT * FROM likes WHERE id = #{id}")
    Likes getLikeById(@Param("id")Long id);
    @Delete("DELETE FROM likes WHERE id=#{id};")
    void cancelLike(@Param("id")Long id);


}


