package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.controller.request.ReplyMessageRequest;
import com.phoenix.huashi.dto.Message.BriefMessage;
import com.phoenix.huashi.entity.Message;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.enums.MessageTypeEnum;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.MessageMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private RecruitProjectMapper recruitProjectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void applyForProject(String userChuangNum, Long projectId) {
        String captainChuangNum = recruitProjectMapper.getCaptianChuangNumByProjectId(projectId);
        Message message = messageMapper.hasApplied(MessageTypeEnum.APPLICATION.getDescription(), projectId, userChuangNum);
        if (message != null ) {
            if(message.getStatus().equals(0)){
                messageMapper.setStatusUpdateTime(message.getId(), timeUtil.getCurrentTimestamp());
                return;
            }
           if(message.getStatus().equals(1)){
               return;
           }
        }
        messageMapper.joinProject(MessageTypeEnum.APPLICATION.getDescription(), projectId, userChuangNum, userMapper.getNicknameByChuangNum(userChuangNum), 0, timeUtil.getCurrentTimestamp(), null, 0, captainChuangNum, userMapper.getNicknameByChuangNum(captainChuangNum));
    }

    @Override
    public String replyMessage(ReplyMessageRequest request) {
        System.out.println(request.getId());
        if (request.getStatus().equals("REFUSE")) {
            messageMapper.updateStatus(-1, request.getReason(), timeUtil.getCurrentTimestamp(), 1, request.getId());
            return "已拒绝";
        } else if (request.getStatus().equals("ACCEPT")) {
            Message message = messageMapper.getMessage(request.getId());
            RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(message.getProjectId());
            if (recruitProject.getMemberNum().equals(recruitProject.getRecruitNum())) {
                messageMapper.updateStatus(-1, "人数已满", timeUtil.getCurrentTimestamp(), 1, request.getId());
                recruitProjectMapper.updateProjectStatusById(message.getProjectId(), 1);
                recruitProjectMapper.setStartTime(message.getProjectId(),timeUtil.getCurrentTimestamp());
                return "人数已满";
            }
            messageMapper.updateStatus(1, null, timeUtil.getCurrentTimestamp(), 1, request.getId());
            memberMapper.insertMember(message.getProjectId(), MemberTypeEnum.MEMBER.getDescription(),0, message.getMemberChuangNum(),null);
            recruitProjectMapper.updateMemberNumberById(message.getProjectId(), recruitProject.getMemberNum() + 1);
            return "已接受";
        }
        return null;
    }

    @Override
    public void projectInvitation(InviteUserRequest request, String captainChuangNum) {
        String memberChuangNum = request.getUserChuangNum();
        Message message = messageMapper.hasInvited(MessageTypeEnum.INVITATION.getDescription(), request.getProjectId(), memberChuangNum);
        if (message != null) {
            messageMapper.setStatusUpdateTime(message.getId(), timeUtil.getCurrentTimestamp());
            return;
        }
        messageMapper.joinProject(MessageTypeEnum.INVITATION.getDescription(), request.getProjectId(), memberChuangNum, userMapper.getNicknameByChuangNum(memberChuangNum), 0, timeUtil.getCurrentTimestamp(), null, 0, captainChuangNum, userMapper.getNicknameByChuangNum(captainChuangNum));
    }

    @Override
    public Page<BriefMessage> getBriefMessageList(GetMessageListReuqest request, String userChuangNum) {

        if (request == null) return null;

        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "statusUpdateTime DESC");

        if (request.getType().equals("ALL")) {
            List<BriefMessage> briefMessageList = messageMapper.getBriefMessageList(userChuangNum);
            return new Page(new PageInfo<>(briefMessageList));
        } else if (request.getType().equals("SEND")) {
            List<BriefMessage> briefMessageList = messageMapper.getBriefMessageSentByMeList(userChuangNum, MessageTypeEnum.INVITATION.getDescription(), MessageTypeEnum.APPLICATION.getDescription());
            return new Page(new PageInfo<>(briefMessageList));
        } else if (request.getType().equals("RECIEVE")) {
            List<BriefMessage> briefMessageList = messageMapper.getBriefMessageSentToMeList(userChuangNum, MessageTypeEnum.INVITATION.getDescription(), MessageTypeEnum.APPLICATION.getDescription());
            return new Page(new PageInfo<>(briefMessageList));
        } else if (request.getType().equals("INVITATION")) {
            List<BriefMessage> messageList = messageMapper.getBriefMessageList(userChuangNum);
            List<BriefMessage> briefMessageList = new ArrayList<>();
            for (BriefMessage message : messageList) {
                if (message.getType().equals(MessageTypeEnum.INVITATION.getDescription())) {
                    briefMessageList.add(message);
                }
            }
            return new Page(new PageInfo<>(briefMessageList));
        } else if (request.getType().equals("APPLICATION")) {
            List<BriefMessage> messageList = messageMapper.getBriefMessageList(userChuangNum);
            List<BriefMessage> briefMessageList = new ArrayList<>();
            for (BriefMessage message : messageList) {
                if (message.getType().equals(MessageTypeEnum.APPLICATION.getDescription())) {
                    briefMessageList.add(message);
                }
            }
            return new Page(new PageInfo<>(briefMessageList));
        }

        return null;
    }

    @Override
    public Object getMessage(Long id, String userChuangNum) {
        messageMapper.updateIsRead(userChuangNum, timeUtil.getCurrentTimestamp());
        Message message = messageMapper.getMessage(id);
       Integer status = message.getStatus();
        String type = message.getType();
        String reason = message.getReason();
        switch (status) {
            case 1:
                return userMapper.getUserInformationByChuangNum(message.getMemberChuangNum());
            case 0:
                if (Objects.equals(type, MessageTypeEnum.APPLICATION.getDescription())) {
                    return userMapper.getUserInformationByChuangNum(message.getMemberChuangNum());
                }
                if (Objects.equals(type, MessageTypeEnum.INVITATION.getDescription())) {
                    return recruitProjectMapper.getRecruitProjectById(message.getProjectId());

                }
                break;
            case -1:
                return reason;
        }
        return null;
    }
}
