package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.CreateProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.UpdateProjectByIdRequest;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.controller.response.GetRecruitProjectResponse;
import com.phoenix.huashi.dto.Message.BriefMessage;
import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.dto.user.RecruitProjectMember;
import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.enums.MemberTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.phoenix.huashi.util.TimeUtil;

@Service

public class RecruitProjectServiceImpl implements RecruitProjectService {
    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public GetRecruitProjectResponse getRecruitProjectById(Long id) {

        List<BriefMember> briefMemberList=memberMapper.getMembersByProjectId(id,0);
        List<RecruitProjectMember> recruitProjectMembers=new ArrayList<>();
        for(BriefMember briefMember:briefMemberList)
        {
            User user=userMapper.getUserByChuangNum(briefMember.getChuangNum());
            RecruitProjectMember recruitProjectMember=new RecruitProjectMember(briefMember.getChuangNum(),user.getName(),user.getTelephone(),user.getQQ(),user.getWechatNum(),user.getMajor(),briefMember.getWork());
            recruitProjectMembers.add(recruitProjectMember);
        }
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(id);
        GetRecruitProjectResponse getRecruitProjectResponse=new GetRecruitProjectResponse(recruitProject,recruitProjectMembers);
        return getRecruitProjectResponse;
    }

    @Override
    public Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefProjectListRequest request) {
        if (request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRList();
        return new Page(new PageInfo<>(briefRecruitProjectList));
    }

    @Override
    public Long createProject(CreateProjectRequest creatTeamRequest) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        Integer status = 0;
        RecruitProject recruitProject=new RecruitProject(null,creatTeamRequest.getName(), creatTeamRequest.getCaptainChuangNum(), creatTeamRequest.getCaptainName(), creatTeamRequest.getInstitute(), creatTeamRequest.getIntroduction(), creatTeamRequest.getBriefDemand(), creatTeamRequest.getTeacherName(), creatTeamRequest.getTeacherApartment(), creatTeamRequest.getTeacherRank(), creatTeamRequest.getPlanStartTime(), creatTeamRequest.getPlanEndTime(), timeUtil.getCurrentTimestamp(),null, creatTeamRequest.getEndTime(), stateUpdateTime, creatTeamRequest.getDemand(), status, creatTeamRequest.getRecruitNum(), creatTeamRequest.getTag1(), creatTeamRequest.getTag2(), creatTeamRequest.getTag3(), 1L);
        recruitProjectMapper.newRecruitProject(recruitProject);
        return recruitProject.getId();
    }

    @Override
    public void applyForDisplayProject(ApplyForDisplayProjectRequest request){
        displayProjectMapper.newDisplayProject(request);
        recruitProjectMapper.deleteRecruitProject(request.getId());
    }

    @Override
    public void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        if (updateProjectByIdRequest.getType().equals(MemberTypeEnum.CAPTAIN.getName())) {
            recruitProjectMapper.updateProjectById(updateProjectByIdRequest.getName(), updateProjectByIdRequest.getCaptainName(), updateProjectByIdRequest.getInstitute(), updateProjectByIdRequest.getIntroduction(), updateProjectByIdRequest.getBriefDemand(), updateProjectByIdRequest.getTeacherName(), updateProjectByIdRequest.getTeacherApartment(), updateProjectByIdRequest.getTeacherRank(), updateProjectByIdRequest.getPlanStartTime(), updateProjectByIdRequest.getPlanEndTime(), updateProjectByIdRequest.getRecruitTime(), updateProjectByIdRequest.getStartTime(), updateProjectByIdRequest.getEndTime(), stateUpdateTime, updateProjectByIdRequest.getDemand(), updateProjectByIdRequest.getStatus(), updateProjectByIdRequest.getRecruitNum(), updateProjectByIdRequest.getTag1(), updateProjectByIdRequest.getTag2(), updateProjectByIdRequest.getTag3(), updateProjectByIdRequest.getProjectId());
        }
    }
}
