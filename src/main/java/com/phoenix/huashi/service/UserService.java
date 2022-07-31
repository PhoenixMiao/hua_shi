package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.member.Experience;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getUserByChuangNum(String userChuangNum);

    void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum);

    Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest);

    Page<BriefProjectInformation> getBriefTeamList(GetTeamListRequest request, String userChuangNum);

    void fillUserInformation(FillUserInformationRequest fillUserInformationRequest,String userChuangNum);

    String resumeUpload(String userChuangNum, MultipartFile file,String fileName);

    List<Experience> getUserProjectExperience(String userChuangNum);

    String resumeDelete(String url,String chuangNum);

    /**
     * 登录
     *
     * @param code
     * @return
     */
    SessionData login(String code);

    String  uploadResumeRTF(String userChuangNum,  String fileName, MultipartFile file);
}
