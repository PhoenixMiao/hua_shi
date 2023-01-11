package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.user.BriefUser;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends MyMapper<User> {
    @Insert("INSERT INTO user(create_time,open_id,union_id,session_key,session_id,nickname) VALUES (#{createTime},#{openId},#{unionId},#{sessionKey},#{sessionId},#{nickname})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    long newUser(User user);

    @Select("SELECT * FROM user WHERE open_id=#{openId};")
    User getUserByOpenId(@Param("openId")String openId);

    @Select("SELECT * FROM user WHERE email=#{emailOrChuangNUm} OR chuangNum=#{emailOrChuangNUm}")
    User getUserByEmailOrChuangNUm(@Param("emailOrChuangNUm")String emailOrChuangNUm);

    @Update("UPDATE user SET chuang_num=#{chuangNum} WHERE id=#{id}")
    void updateChuangNum(@Param("chuangNum") String chuangNum, @Param("id") Long id);



    @Select("SELECT * FROM user")
    List<User> getUserList();

    @Update("UPDATE user SET nickname=#{nickname},name=#{name},gender=#{gender},school=#{school},department=#{department},major=#{major},grade=#{grade},telephone=#{telephone},q_q=#{qq},wechat_num=#{wechatNum},portrait=#{portrait},resume=#{resume},attachment=#{attachment},student_number=#{studentNumber} WHERE chuang_num=#{chuangNum}")
    void fillUserInformation(@Param("chuangNum") String chuangNum,
                         @Param("nickname") String nickname,
                         @Param("name") String name,
                         @Param("gender") Integer gender,
                         @Param("school")  String school,
                         @Param("department") String department,
                         @Param("major") String major,
                         @Param("grade") String grade,
                         @Param("telephone") String telephone,
                         @Param("qq") String QQ,
                         @Param("wechatNum") String wechatNum,
                         @Param("portrait") String portrait,
                         @Param("resume") String resume,
                         @Param("attachment") String attachment,
                         @Param("studentNumber") String studentNumber);


    @Update("UPDATE user SET nickname = #{nickname},portrait = #{portrait},telephone = #{telephone},department=#{department},major=#{major},grade=#{grade},q_q=#{QQ},wechat_num=#{wechatNum},resume=#{resume},attachment=#{attachment} WHERE chuang_num=#{chuangNum}")
    void updateUserByChuangNum(@Param("nickname") String nickname,
                               @Param("portrait") String portrait,
                               @Param("telephone") String telephone,
                               @Param("department") String department,
                               @Param("major") String major,
                               @Param("grade") String grade,
                               @Param("QQ") String QQ,
                               @Param("wechatNum") String wechatNum,
                               @Param("resume") String resume,
                               @Param("attachment") String attachment,
                               @Param("chuangNum") String chuangNum);

    @Select("SELECT name,chuang_num FROM user WHERE name LIKE concat('%',#{name},'%')")
    List<BriefUserName> searchBriefUserNameListByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE chuang_num=#{chuangNum}")
    User getUserByChuangNum(@Param("chuangNum") String chuangNum);

    @Select("SELECT nickname FROM user WHERE chuang_num=#{chuangNum}")
    String getNicknameByChuangNum(@Param("chuangNum") String chuangNum);

    @Select("SELECT name,major,grade,telephone,q_q,resume,student_number FROM user WHERE chuang_num=#{chuangNum}")
    BriefUser getUserInformationByChuangNum(@Param("chuangNum") String chuangNum);
}


