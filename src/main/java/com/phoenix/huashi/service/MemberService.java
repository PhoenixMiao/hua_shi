package com.phoenix.huashi.service;

import com.phoenix.huashi.controller.request.AddMemberRequest;
import com.phoenix.huashi.controller.request.AssignWorkRequest;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;

public interface MemberService {
    String addMember(AddMemberRequest reuqest);

    void assignWork(AssignWorkRequest assignWorkRequest,String userChuangNum);
}
