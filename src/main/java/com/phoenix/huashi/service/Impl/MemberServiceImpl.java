package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.controller.request.AddMemberRequest;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.service.MemberService;
import com.phoenix.huashi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Autowired
    private TimeUtil timeUtil;

    @Override
    public String addMember(AddMemberRequest reuqest)
    {
        RecruitProject recruitProject=recruitProjectMapper.getRecruitProjectById(reuqest.getRecruitProjectId());
        if(recruitProject.getRecruitNum()<=recruitProject.getMemberNum()){
            return "人员已满";
        }
        memberMapper.insertMember(reuqest.getRecruitProjectId(), MemberTypeEnum.valueOf(reuqest.getType()).getDescription(),0,reuqest.getMemberChuangNum(),reuqest.getWork());
        recruitProjectMapper.updateMemberNumberById(reuqest.getRecruitProjectId(),recruitProject.getMemberNum()+1);
        if(recruitProject.getRecruitNum().equals(recruitProject.getMemberNum())){
            recruitProjectMapper.updateProjectStatusById(reuqest.getRecruitProjectId(),1,timeUtil.getCurrentTimestamp());
        }
        return "添加成功";
    }

}
