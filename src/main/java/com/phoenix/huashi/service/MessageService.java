package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.dto.Message.BriefMessage;

public interface MessageService {
    void applyForProject(String userChuangNum, Long projectId);

    void projectInvitation(InviteUserRequest request, String captainChuangNum);

    Page<BriefMessage> getBriefMessageList(GetMessageListReuqest request, String userChuangNum);

    Object getMessage(Long id, String userChuangNum);

    String replyMessage(ReplyMessageRequest request);

    String auditProject(UpdateDisplayProjectStatusRequest request);

}
