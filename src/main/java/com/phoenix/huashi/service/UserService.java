package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetBriefUserNameListRequest;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.controller.request.UpdateUserByChuangNumRequest;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;

public interface UserService {
    User getUserByChuangNum(String userChuangNum);
    void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum);
    Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest);
    Page<BriefProjectInformation> getBriefTeamList(GetListRequest request,String userChuangNum);


    /**
     * 登录
     * @param code
     * @return
     */
    SessionData login(String code);
}
