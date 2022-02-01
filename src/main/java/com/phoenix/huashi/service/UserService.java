package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetBriefUserNameListRequest;
import com.phoenix.huashi.controller.request.UpdateUserByIdRequest;
import com.phoenix.huashi.controller.response.GetUserByIdResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.user.BriefUserName;

public interface UserService {
    GetUserByIdResponse getUserById(Long id);
    void updateUserById(UpdateUserByIdRequest updateUserByIdRequest, Long id);
    Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest);



    /**
     * 登录
     * @param code
     * @return
     */
    SessionData login(String code);
}
