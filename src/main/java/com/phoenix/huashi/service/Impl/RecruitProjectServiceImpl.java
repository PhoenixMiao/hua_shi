package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetRecruitProjectResponse;
import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.dto.recruitproject.BriefApplication;
import com.phoenix.huashi.dto.recruitproject.BriefRecruitProject;
import com.phoenix.huashi.dto.user.BriefUser;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.dto.user.RecruitProjectMember;
import com.phoenix.huashi.entity.*;
import com.phoenix.huashi.enums.MessageTypeEnum;
import com.phoenix.huashi.mapper.*;
import com.phoenix.huashi.service.RecruitProjectService;
import com.phoenix.huashi.enums.MemberTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.phoenix.huashi.util.TimeUtil;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private MessageMapper messageMapper;

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
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "state_update_time desc");
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRList();
        return new Page(new PageInfo<>(briefRecruitProjectList));
    }

    @Override
    public Long createProject(CreateProjectRequest creatTeamRequest,String userChuangNum) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        Integer status = 0;
        String chuangNum=creatTeamRequest.getCaptainChuangNum();
        if(chuangNum==null)chuangNum=userChuangNum;
        RecruitProject recruitProject=new RecruitProject(null,creatTeamRequest.getName(), chuangNum, userMapper.getUserByChuangNum(chuangNum).getName(), creatTeamRequest.getInstitute(), creatTeamRequest.getIntroduction(), creatTeamRequest.getBriefDemand(), creatTeamRequest.getTeacherName(), creatTeamRequest.getTeacherApartment(), creatTeamRequest.getTeacherRank(), creatTeamRequest.getPlanStartTime(), creatTeamRequest.getPlanEndTime(), timeUtil.getCurrentTimestamp(),timeUtil.getCurrentTimestamp(), null, stateUpdateTime, creatTeamRequest.getDemand(), status, creatTeamRequest.getRecruitNum(), creatTeamRequest.getTag1(), creatTeamRequest.getTag2(), creatTeamRequest.getTag3(), 1L);
        recruitProjectMapper.newRecruitProject(recruitProject);
        memberMapper.insertMember(recruitProject.getId(),MemberTypeEnum.CAPTAIN.getDescription(),0, chuangNum, "组长");
        return recruitProject.getId();
    }

//    @Override
//    public void applyForDisplayProject(ApplyForDisplayProjectRequest request){
//        DisplayProject displayProject=new DisplayProject(null,request.getYear(),request.getInstitute(),request.getName(),request.getCaptainChuangNum(),request.getCaptainName(),request.getTeacherName(),request.getTeacherApartment(),request.getTeacherRank(),request.getTeacherStudy(),request.getUploadTime(),request.getIntroduction(),
//                request.getType(),request.getMajor(),request.getNumber(),request.getAward(),request.getInnovation(),request.getLikes(),request.getCollections(),request.getPaper(), request.getDeadline(), request.getPersonLimit(),request.getMemberOneName(), request.getMemberOneGrade(), request.getMemberOneMajor(), request.getMemberTwoName(),
//                request.getMemberTwoGrade(), request.getMemberTwoMajor(), request.getMemberThreeName(), request.getMemberThreeGrade(), request.getMemberThreeMajor(), request.getMemberFourName(), request.getMemberFourGrade(), request.getMemberFourMajor(), request.getMemberFiveName(), request.getMemberFiveGrade(), request.getMemberFiveMajor());
//        displayProjectMapper.insert(displayProject);
//        recruitProjectMapper.deleteRecruitProject(request.getId());
//    }

    @Override
    public void updateProjectStatusById(UpdateProjectStatusRequest updateProjectStatusRequest){
        recruitProjectMapper.updateProjectStatusById(updateProjectStatusRequest.getProjectId(),updateProjectStatusRequest.getNewStatus(),timeUtil.getCurrentTimestamp());
    }

    @Override
    public Integer getApplications(Long projectId) {
       List<Message> messageList=messageMapper.getApplication(MessageTypeEnum.APPLICATION.getDescription(),projectId);
       return messageList.size();
    }

    @Override
    public Page<BriefApplication> getBriefApplicationList(GetBriefApplicationListRequest request) {
        List<Message> messageList=messageMapper.getApplication(MessageTypeEnum.APPLICATION.getDescription(), request.getId());
        List<BriefApplication> briefApplications=new ArrayList<>();
        for(Message message:messageList){
            BriefUser user=userMapper.getUserInformationByChuangNum(message.getMemberChuangNum());
            BriefApplication briefApplication=new BriefApplication(message.getId(),message.getMemberChuangNum(),user.getName(), user.getMajor(), user.getGrade(), user.getTelephone(), user.getQQ(), message.getIsRead(), message.getStatusUpdateTime(),message.getStatus());
            briefApplications.add(briefApplication);
        }
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        return new Page(new PageInfo<>(briefApplications));
    }

    @Override
    public Page<BriefRecruitProject> searchRecruitProject(SearchRequest searchRequest) {
        Example example = new Example(RecruitProject.class);
        Example.Criteria statusCriteria = example.createCriteria();
        statusCriteria.andEqualTo("status", 0);

        if (!StringUtils.isEmpty(searchRequest.getDepartment())) {
            Example.Criteria departmentCriteria = example.createCriteria();
            departmentCriteria.orLike("institute", "%" + searchRequest.getDepartment() + "%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("name", "%" + searchRequest.getName() + "%");
            example.and(nameCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getCaptain())) {
            List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchRequest.getCaptain());
            Example.Criteria captainCriteria = example.createCriteria();
            for (BriefUserName ele : briefUserNameList)
            {
                captainCriteria.orEqualTo("captainChuangNum", ele.getChuangNum());
            }

            example.and(captainCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getTag())) {
            Example.Criteria tagCriteria = example.createCriteria();
            tagCriteria.orLike("tag1", "%" + searchRequest.getTag() + "%");
            tagCriteria.orLike("tag2", "%" + searchRequest.getTag() + "%");
            tagCriteria.orLike("tag3", "%" + searchRequest.getTag() + "%");
            example.and(tagCriteria);
        }
        example.orderBy("id").desc();

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                "state_update_time desc");
        List<RecruitProject> recruitProjectList = recruitProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(recruitProjectList));

        ArrayList<BriefRecruitProject> searchResponseArrayList = new ArrayList<>();
        for (RecruitProject ele : recruitProjectList) {
            searchResponseArrayList.add(new BriefRecruitProject(ele.getId(), ele.getName(), ele.getTag1(), ele.getTag2(),ele.getTag3(),ele.getBriefDemand(),ele.getStatus()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }


    @Override
    public void updateProjectById(UpdateProjectByIdRequest updateProjectByIdRequest) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        if (updateProjectByIdRequest.getType().equals(MemberTypeEnum.CAPTAIN.getName())) {
            recruitProjectMapper.updateProjectById(updateProjectByIdRequest.getName(), updateProjectByIdRequest.getCaptainName(), updateProjectByIdRequest.getInstitute(), updateProjectByIdRequest.getIntroduction(), updateProjectByIdRequest.getBriefDemand(), updateProjectByIdRequest.getTeacherName(), updateProjectByIdRequest.getTeacherApartment(), updateProjectByIdRequest.getTeacherRank(), updateProjectByIdRequest.getPlanStartTime(), updateProjectByIdRequest.getPlanEndTime(), updateProjectByIdRequest.getRecruitTime(), updateProjectByIdRequest.getStartTime(), updateProjectByIdRequest.getEndTime(), stateUpdateTime, updateProjectByIdRequest.getDemand(), updateProjectByIdRequest.getStatus(), updateProjectByIdRequest.getRecruitNum(), updateProjectByIdRequest.getTag1(), updateProjectByIdRequest.getTag2(), updateProjectByIdRequest.getTag3(), updateProjectByIdRequest.getProjectId());
        }
    }
}
