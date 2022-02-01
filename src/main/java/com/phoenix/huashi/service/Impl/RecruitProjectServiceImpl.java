package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.CreatTeamRequest;
import com.phoenix.huashi.controller.request.GetBriefListRequest;
import com.phoenix.huashi.controller.request.UpdateTeamByIdRequest;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.enums.MemberTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;
import com.phoenix.huashi.util.TimeUtil;
@Service

public class RecruitProjectServiceImpl implements RecruitProjectService {
    @Autowired
    private RecruitProjectMapper recruitProjectMapper;
    @Autowired
    private  TimeUtil timeUtil;

    @Override
    public RecruitProject getRecruitProjectById(Long id) {
 RecruitProject recruitProject=recruitProjectMapper.getRecruitProjectById(id);
        return recruitProject;

    }

    @Override
    public Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefListRequest request) {
        if(request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize(),pageParam.getOrderBy());
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRList();
        return new Page(new PageInfo<>(briefRecruitProjectList));
    }

    @Override
    public void creatTeam(CreatTeamRequest creatTeamRequest,Long userId) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        Integer status=0;
        recruitProjectMapper.creatTeam(creatTeamRequest.getName(), creatTeamRequest.getCaptainChuangNum(), creatTeamRequest.getCaptainName(), creatTeamRequest.getInstitute(), creatTeamRequest.getIntroduction(), creatTeamRequest.getBriefDemand(),  creatTeamRequest.getTeacherName(), creatTeamRequest.getTeacherApartment(), creatTeamRequest.getTeacherRank(), creatTeamRequest.getPlanStartTime(), creatTeamRequest.getPlanEndTime(), creatTeamRequest.getRecruitTime(), creatTeamRequest.getStartTime(), creatTeamRequest.getEndTime(), stateUpdateTime, creatTeamRequest.getDemand(), status,creatTeamRequest.getRecruitNum(), creatTeamRequest.getTag1(), creatTeamRequest.getTag2(), creatTeamRequest.getTag3());
    }

    @Override
    public void updateTeamById(UpdateTeamByIdRequest updateTeamByIdRequest, Long id) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        if (updateTeamByIdRequest.getType().equals(MemberTypeEnum.CAPTAIN.getName())) {
            recruitProjectMapper.updateTeamById(updateTeamByIdRequest.getName(), updateTeamByIdRequest.getCaptainName(), updateTeamByIdRequest.getInstitute(), updateTeamByIdRequest.getIntroduction(), updateTeamByIdRequest.getBriefDemand(), updateTeamByIdRequest.getTeacherName(), updateTeamByIdRequest.getTeacherApartment(), updateTeamByIdRequest.getTeacherRank(), updateTeamByIdRequest.getPlanStartTime(), updateTeamByIdRequest.getPlanEndTime(), updateTeamByIdRequest.getRecruitTime(), updateTeamByIdRequest.getStartTime(), updateTeamByIdRequest.getEndTime(), stateUpdateTime, updateTeamByIdRequest.getDemand(), updateTeamByIdRequest.getStatus(), updateTeamByIdRequest.getRecruitNum(), id, updateTeamByIdRequest.getTag1(), updateTeamByIdRequest.getTag2(), updateTeamByIdRequest.getTag3());
        }
    }}
