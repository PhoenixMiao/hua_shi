package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;

import java.util.List;

public interface UserService {
    User getUserByChuangNum(String userChuangNum);

    void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum);

    Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest);

    Page<BriefProjectInformation> getBriefTeamList(GetTeamListRequest request, String userChuangNum);

    void fillUserInformation(FillUserInformationRequest fillUserInformationRequest,String userChuangNum);


    /**
     * 登录
     *
     * @param code
     * @return
     */
    SessionData login(String code);
}
