package com.phoenix.huashi.mapper;

import com.phoenix.huashi.dto.Message.BriefMessage;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Message;
import com.phoenix.huashi.enums.MessageTypeEnum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    @Insert("INSERT INTO message(type,projectId,memberChuangNum,memberNickname,status,statusUpdateTime,reason,isRead,projectCaptainChuangNum,projectCaptainNickname) VALUE(#{type},#{projectId},#{memberChuangNum},#{memberNickname},#{status},#{statusUpdateTime},#{reason},#{isRead},#{projectCaptainChuangNum},#{projectCaptainNickname})")
    void joinProject(
            @Param("type") String type,
            @Param("projectId") Long projectId,
            @Param("memberChuangNum") String memberChuangNum,
            @Param("memberNickname") String memberNickname,
            @Param("status") Integer status,
            @Param("statusUpdateTime") String statusUpdateTime,
            @Param("reason") String reason,
            @Param("isRead") Integer isRead,
            @Param("projectCaptainChuangNum") String projectCaptainChuangNum,
            @Param("projectCaptainNickname") String projectCaptainNickname);

    @Select("SELECT  id,type,projectId,memberChuangNum,memberNickname,status,isRead,projectCaptainChuangNum,projectCaptainNickname FROM message WHERE projectCaptainChuangNum=#{ChuangNum} or memberChuangNum=#{ChuangNum} ")
    List<BriefMessage> getBriefMessageList(@Param("ChuangNum") String ChuangNum);

    @Select("SELECT  id,type,projectId,memberChuangNum,memberNickname,status,isRead,projectCaptainChuangNum,projectCaptainNickname FROM message WHERE (projectCaptainChuangNum=#{ChuangNum} AND type=#{invite}) OR (memberChuangNum=#{ChuangNum} AND type=#{apply})")
    List<BriefMessage> getBriefMessageSentByMeList(@Param("ChuangNum") String ChuangNum, @Param("invite") String invite, @Param("apply") String apply);

    @Select("SELECT  id,type,projectId,memberChuangNum,memberNickname,status,isRead,projectCaptainChuangNum,projectCaptainNickname FROM message WHERE (projectCaptainChuangNum=#{ChuangNum} AND type=#{apply}) OR (memberChuangNum=#{ChuangNum} AND type=#{invite}) ")
    List<BriefMessage> getBriefMessageSentToMeList(@Param("ChuangNum") String ChuangNum, @Param("invite") String invite, @Param("apply") String apply);

    @Select("SELECT * FROM message WHERE type=#{type} AND projectId=#{projectId} AND memberChuangNum=#{memberChuangNum} ")
    Message hasApplied(@Param("type") String type, @Param("projectId") Long projectId, @Param("memberChuangNum") String memberChuangNum);

    @Select("SELECT * FROM message WHERE type=#{type} AND memberChuangNum=#{memberChuangNum} AND projectId=#{projectId}")
    Message hasInvited(@Param("type") String type, @Param("projectId") Long projectId, @Param("memberChuangNum") String memberChuangNum);

    @Update("UPDATE message SET isRead=1,statusUpdateTime=#{statusUpdateTime} WHERE projectCaptainChuangNum=#{ChuangNum} or memberChuangNum=#{ChuangNum}")
    void updateIsRead(
            @Param("ChuangNum") String ChuangNum,
            @Param("statusUpdateTime") String statusUpdateTime
    );

    @Update("UPDATE message SET status=#{status},reason=#{reason},statusUpdateTime=#{statusUpdateTime},isRead=#{isRead} WHERE id=#{id} ")
    void updateStatus(
            @Param("status") Integer status,
            @Param("reason") String reason,
            @Param("statusUpdateTime") String statusUpdateTime,
            @Param("isRead") Integer isRead,
            @Param("id") Long id
    );

    @Select("SELECT  * FROM message WHERE id=#{id}")
    Message getMessage(@Param("id") Long id);

    @Update("UPDATE message SET statusUpdateTime=#{statusUpdateTime} WHERE id=#{id}")
    void setStatusUpdateTime(
            @Param("id") Long id,
            @Param("statusUpdateTime") String statusUpdateTime
    );
}

