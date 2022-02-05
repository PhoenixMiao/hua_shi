package com.phoenix.huashi.mapper;

import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Message;
import com.phoenix.huashi.enums.MessageTypeEnum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    @Insert("INSERT INTO message(type,projectId,memberChuangNum,memberNickname,status,statusUpdateTime,reason,isRead,projectPrincipalChuangNum,projectPrincipalNickname) VALUE(#{type},#{projectId},#{memberChuangNum},#{memberNickname},#{status},#{statusUpdateTime},#{reason},#{isRead},#{projectPrincipalChuangNum},#{projectPrincipalNickname})")
    void joinProject(
            @Param("type") String type,
            @Param ("projectId")Long projectId,
            @Param("memberChuangNum")String memberChuangNum,
            @Param("memberNickname")String memberNickname,
            @Param("status")Integer status,
            @Param("statusUpdateTime")String statusUpdateTime,
            @Param("reason")String reason,
            @Param("isRead")Integer isRead,
            @Param("projectPrincipalChuangNum")String projectPrincipalChuangNum,
            @Param("projectPrincipalNickname")String projectPrincipalNickname);

    @Select("SELECT  * FROM message WHERE projectPrincipalChuangNum=#{ChuangNum} or memberChuangNum=#{ChuangNum}")
    List<Message> getBriefMessageList(@Param("ChuangNum") String ChuangNum);

    @Select("SELECT * FROM message WHERE (projectPrincipalChuangNum=#{ChuangNum} AND type=#{invite}) OR (memberChuangNum=#{ChuangNum} AND type={apply}) ")
    List<Message> getBriefMessageSentByMeList(@Param("ChuangNum") String ChuangNum,@Param("invite") String invite,@Param("apply") String apply);

    @Select("SELECT * FROM message WHERE type=#{type} AND projectId=#{projectId} AND memberChuangNum=#{memberChuangNum} ")
    Message hasApplied(@Param("type") String type,@Param("projectId") Long projectId,@Param("memberChuangNum") String memberChuangNum);

    @Select("SELECT * FROM message WHERE type=#{type} AND memberChuangNum=#{memberChuangNum} AND projectId=#{projectId}")
    Message hasInvited(@Param("type") String type,@Param("projectId") Long projectId,@Param("memberChuangNum") String memberChuangNum);
}
