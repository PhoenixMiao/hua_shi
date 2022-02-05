package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.enums.MessageTypeEnum;
import com.phoenix.huashi.mapper.MessageMapper;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TimeUtil timeUtil;
    @Override
    public void applyForProject(String userChuangNum,Long projectId)
    {
        messageMapper.joinProject(MessageTypeEnum.APPLICATION.getDescription(),projectId,userChuangNum,0,timeUtil.getCurrentTimestamp(),null,0);
    }
    @Override
    public void projectInvitation(InviteUserRequest request)
    {
        messageMapper.joinProject(MessageTypeEnum.INVITATION.getDescription(), request.getProjectId(), request.getUserChuangNum(), 0,timeUtil.getCurrentTimestamp(),null,0);
    }

}
