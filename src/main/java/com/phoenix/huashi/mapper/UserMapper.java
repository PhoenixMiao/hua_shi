package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.user.BriefUser;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends MyMapper<User> {
    @Insert("INSERT INTO user(createTime,openId,unionId,sessionKey,sessionId) VALUES (#{createTime},#{openId},#{unionId},#{sessionKey},#{sessionId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long newUser(User user);

    @Select("SELECT * FROM user WHERE openId=#{openId};")
    User getUserByOpenId(@Param("openId")String openId);


    @Update("UPDATE user SET chuangNum=#{chuangNum} WHERE id=#{id}")
    void updateChuangNum(@Param("chuangNum") String chuangNum, @Param("id") Long id);


    @Update("UPDATE user SET nickname = #{nickname},portrait = #{portrait},school = #{school},telephone = #{telephone},department=#{department},major=#{major},grade=#{grade},QQ=#{QQ},wechatNum=#{wechatNum},resume=#{resume},attachment=#{attachment} WHERE chuangNum=#{chuangNum}")
    void updateUserByChuangNum(@Param("nickname") String nickname,
                               @Param("portrait") String portrait,
                               @Param("telephone") String telephone,
                               @Param("school") String school,
                               @Param("department") String department,
                               @Param("major") String major,
                               @Param("grade") String grade,
                               @Param("QQ") String QQ,
                               @Param("wechatNum") String wechatNum,
                               @Param("resume") String resume,
                               @Param("attachment") String attachment,
                               @Param("chuangNum") String chuangNum);

    @Select("SELECT name,chuangNum FROM user WHERE name LIKE %{name}%")
    List<BriefUserName> searchBriefUserNameListByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE chuangNum=#{chuangNum}")
    User getUserByChuangNum(@Param("chuangNum") String chuangNum);

    @Select("SELECT nickname FROM user WHERE chuangNum=#{chuangNum}")
    String getNicknameByChuangNum(@Param("chuangNum") String chuangNum);

    @Select("SELECT name,major,grade,telephone,QQ,resume,studentNumber FROM user WHERE chuangNum=#{chuangNum}")
    BriefUser getUserInformationByChuangNum(@Param("chuangNum") String chuangNum);
}


