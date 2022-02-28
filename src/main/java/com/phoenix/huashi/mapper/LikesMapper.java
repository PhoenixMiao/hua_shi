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


    @Insert("INSERT INTO likes(id,project_id,user_chuang_num,like_time) VALUE(null,#{project_id},#{user_chuang_num},#{like_time})")
    void addToLikes(
            @Param("project_id") Long projectId,
            @Param("user_chuang_num") String userChuangNum,
            @Param("like_time") String likeTime);

    @Select("SELECT * FROM likes WHERE projectId = #{projectId} AND userChuangNum=#{userChuangNum}")
    Likes getLikeByProjectIdAndUserChuangNum(@Param("projectId") Long projectId,@Param("userChuangNum")String userChuangNum);

    @Delete("DELETE FROM likes WHERE id=#{id};")
    void cancelLike(@Param("id") Long id);


}


