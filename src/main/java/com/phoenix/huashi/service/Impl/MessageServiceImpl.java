package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.dto.Message.BriefMessage;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.entity.Message;
import com.phoenix.huashi.enums.MessageTypeEnum;
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

    @Override
    public void applyForProject(String userChuangNum,Long projectId)
    {
        String captainChuangNum=recruitProjectMapper.getCaptianChuangNumByProjectId(projectId);
        Message message=messageMapper.hasApplied(MessageTypeEnum.APPLICATION.getDescription(),projectId,userChuangNum);
        if(message!=null)return ;
        messageMapper.joinProject(MessageTypeEnum.APPLICATION.getDescription(),projectId,userChuangNum,userMapper.getNicknameByChuangNum(userChuangNum),0,timeUtil.getCurrentTimestamp(),null,0,captainChuangNum,userMapper.getNicknameByChuangNum(captainChuangNum));
    }
    @Override
    public void projectInvitation(InviteUserRequest request,String captainChuangNum)
    {
        String memberChuangNum=request.getUserChuangNum();
        Message message=messageMapper.hasInvited(MessageTypeEnum.INVITATION.getDescription(), request.getProjectId(),memberChuangNum);
        if(message!=null)return ;
        messageMapper.joinProject(MessageTypeEnum.INVITATION.getDescription(), request.getProjectId(), memberChuangNum,userMapper.getNicknameByChuangNum(memberChuangNum), 0,timeUtil.getCurrentTimestamp(),null,0,captainChuangNum,userMapper.getNicknameByChuangNum(captainChuangNum));
    }
    @Override
    public Page<BriefMessage> getMessageList(GetMessageListReuqest request, String userChuangNum)
    {

        List<Message> messageList=new ArrayList<>();
        HashSet set = new HashSet(messageList);
        messageList.clear();
        messageList.addAll(set);
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        if(request.getType()=="ALL")
        {
            messageList=messageMapper.getBriefMessageList(userChuangNum);
        }
        else if(request.getType()=="SEND")
        {
            messageList=messageMapper.getBriefMessageSentByMeList(userChuangNum,MessageTypeEnum.INVITATION.getDescription(),MessageTypeEnum.APPLICATION.getDescription());
        }
        else if(request.getType()=="RECIEVE")
        {
            messageList=messageMapper.getBriefMessageSentByMeList(userChuangNum,MessageTypeEnum.INVITATION.getDescription(),MessageTypeEnum.APPLICATION.getDescription());
        }
        else if(request.getType()=="INVITATION")
        {
            List<Message> allMessageList=messageMapper.getBriefMessageList(userChuangNum);
            for(Message message:allMessageList){
                if(message.getType()==MessageTypeEnum.INVITATION.getDescription()){
                    messageList.add(message);
                }
            }
        }
        else if(request.getType()=="APPLICATION")
        {
            List<Message> allMessageList=messageMapper.getBriefMessageList(userChuangNum);
            for(Message message:allMessageList){
                if(message.getType()==MessageTypeEnum.APPLICATION.getDescription()){
                    messageList.add(message);
                }
            }
        }

        List<BriefMessage> briefMessageList = new ArrayList<>();
        for(Message message:messageList)
        {
            BriefMessage briefMessage=new BriefMessage(message.getId(),message.getProjectId(),message.getMemberChuangNum(),message.getMemberNickname(),message.getStatus(),message.getIsRead(),message.getProjectPrincipalChuangNum(),message.getProjectPrincipalNickname());
            briefMessageList.add(briefMessage);
        }
        return  new Page(new PageInfo<>(briefMessageList));
    }
}
