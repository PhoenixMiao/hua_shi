package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.dto.Message.BriefMessage;

public interface MessageService {
    void applyForProject(String userChuangNum,Long projectId);
    void projectInvitation(InviteUserRequest request,String captainChuangNum);
    Page<BriefMessage> getMessageList(GetMessageListReuqest request,String userChuangNum);
}
