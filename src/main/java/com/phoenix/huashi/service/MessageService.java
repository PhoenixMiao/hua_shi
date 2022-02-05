package com.phoenix.huashi.service;

import com.phoenix.huashi.controller.request.InviteUserRequest;

public interface MessageService {
    void applyForProject(String userChuangNum,Long projectId);
    void projectInvitation(InviteUserRequest request);
}
