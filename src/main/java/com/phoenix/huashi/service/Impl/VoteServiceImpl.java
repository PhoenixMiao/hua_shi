package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.service.VoteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.entity.*;
import com.phoenix.huashi.enums.MessageTypeEnum;
import com.phoenix.huashi.mapper.*;
import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.util.AssertUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.phoenix.huashi.util.TimeUtil;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private UserMapper userMapper;


    @Scheduled(cron="0 0 2  * * ? ")
    @Override
    public void updateVote() {
        List<Vote> voteList=voteMapper.getVote();
        List<User> userList= userMapper.getUserList();
        for (Vote vote:voteList){
            if(vote!=null){
            displayProjectMapper.updateVote(vote.getProjectId());
            }
        }
        for(User user:userList){
            user.setVote(0);
            userMapper.updateByPrimaryKey(user);
        }
        voteMapper.deleteVote();
    }

    @Override
    public String vote(Long projectId, String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        if(user.getVote() == 3)
            throw new CommonException(CommonErrorCode.VOTES_MAXIMUM_REACHED);
        voteMapper.addToVote(projectId,userChuangNum);
        user.setVote(user.getVote() + 1);
        userMapper.updateByPrimaryKey(user);
        return "投票成功";
    }
}
