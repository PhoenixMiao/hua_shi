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
    @Insert("INSERT INTO message(type,project_name,project_id,member_chuang_num,member_nickname,status,status_update_time,reason,is_read,project_captain_chuang_num,project_captain_nickname) VALUE(#{type},#{project_name},#{project_id},#{member_chuang_num},#{member_nickname},#{status},#{status_update_time},#{reason},#{is_read},#{project_captain_chuang_num},#{project_captain_nickname})")
    void joinProject(
            @Param("type") String type,
            @Param("project_name") String projectName,
            @Param("project_id") Long projectId,
            @Param("member_chuang_num") String memberChuangNum,
            @Param("member_nickname") String memberNickname,
            @Param("status") Integer status,
            @Param("status_update_time") String statusUpdateTime,
            @Param("reason") String reason,
            @Param("is_read") Integer isRead,
            @Param("project_captain_chuang_num") String projectCaptainChuangNum,
            @Param("project_captain_nickname") String projectCaptainNickname);

    @Select("SELECT id,project_captain_chuang_num,project_captain_nickname,type,project_id,project_name,member_chuang_num,member_nickname,status,is_read FROM message WHERE project_captain_chuang_num=#{chuang_num} or member_chuang_num=#{chuang_num} ")
    List<BriefMessage> getBriefMessageList(@Param("chuang_num") String ChuangNum);

    @Select("SELECT id,type,project_id,project_name,member_chuang_num,member_nickname,status,is_read,project_captain_chuang_num,project_captain_nickname FROM message WHERE (project_captain_chuang_num=#{chuang_num} AND type=#{invite}) OR (member_chuang_num=#{chuang_num} AND type=#{apply})")
    List<BriefMessage> getBriefMessageSentByMeList(@Param("chuang_num") String ChuangNum, @Param("invite") String invite, @Param("apply") String apply);

    @Select("SELECT id,type,project_id,project_name,member_chuang_num,member_nickname,status,is_read,project_captain_chuang_num,project_captain_nickname FROM message WHERE (project_captain_chuang_num=#{chuang_num} AND type=#{apply}) OR (member_chuang_num=#{chuang_num} AND type=#{invite}) ")
    List<BriefMessage> getBriefMessageSentToMeList(@Param("chuang_num") String ChuangNum, @Param("invite") String invite, @Param("apply") String apply);

    @Select("SELECT id,project_captain_chuang_num,project_captain_nickname,type,project_id,project_name,member_chuang_num,member_nickname,status,is_read FROM message WHERE type=#{type} AND (project_captain_chuang_num=#{chuang_num} OR member_chuang_num=#{chuang_num}) ")
    List<BriefMessage> getBriefApplicationOrInvitationMessageList(@Param("chuang_num") String ChuangNum,@Param("type") String type);
    @Select("SELECT * FROM message WHERE type=#{type} AND project_id=#{projectId} AND member_chuang_num=#{memberChuangNum} ")
    Message hasApplied(@Param("type") String type, @Param("projectId") Long projectId, @Param("memberChuangNum") String memberChuangNum);

    @Select("SELECT * FROM message WHERE type=#{type} AND member_chuang_num=#{memberChuangNum} AND project_id=#{projectId}")
    Message hasInvited(@Param("type") String type, @Param("projectId") Long projectId, @Param("memberChuangNum") String memberChuangNum);

    @Update("UPDATE message SET is_read=1,status_update_time=#{statusUpdateTime} WHERE project_captain_chuang_num=#{ChuangNum} or member_chuang_num=#{ChuangNum}")
    void updateIsRead(
            @Param("ChuangNum") String ChuangNum,
            @Param("statusUpdateTime") String statusUpdateTime
    );

    @Update("UPDATE message SET status=#{status},reason=#{reason},status_update_time=#{statusUpdateTime},is_read=#{isRead} WHERE id=#{id} ")
    void updateStatus(
            @Param("status") Integer status,
            @Param("reason") String reason,
            @Param("statusUpdateTime") String statusUpdateTime,
            @Param("isRead") Integer isRead,
            @Param("id") Long id
    );

    @Select("SELECT  * FROM message WHERE id=#{id}")
    Message getMessage(@Param("id") Long id);

    @Update("UPDATE message SET status_update_time=#{statusUpdateTime} WHERE id=#{id}")
    void setStatusUpdateTime(
            @Param("id") Long id,
            @Param("statusUpdateTime") String statusUpdateTime
    );

    @Select("SELECT * FROM message WHERE type=#{type} AND project_id=#{projectId}  ")
    List<Message> getApplication(@Param("type") String type, @Param("projectId") Long projectId);
}

