package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

public interface UserMapper extends MyMapper<User> {
    @Insert("INSERT INTO user(createTime,openId,unionId,sessionKey,sessionId) VALUES (#{createTime},#{openId},#{unionId},#{sessionKey},#{sessionId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    Long newUser(User user);

    @Update("UPDATE user SET chuangNum=#{chuangNum} WHERE id=#{id}")
    void updateChuangNum(@Param("chuangNum")String chuangNum,@Param("id")Long id);
}
