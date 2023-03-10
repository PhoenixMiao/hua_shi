package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
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
import com.phoenix.huashi.util.AssertUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.phoenix.huashi.util.TimeUtil;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import static com.phoenix.huashi.common.CommonConstants.COS_BUCKET_NAME;

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


    @Autowired
    private TransferManager transferManager;

    @Autowired
    private COSClient cosClient;

    @Override
    public GetRecruitProjectResponse getRecruitProjectById(Long id) {

        List<BriefMember> briefMemberList = memberMapper.getMembersByProjectId(id, 0);
        List<RecruitProjectMember> recruitProjectMembers = new ArrayList<>();
        for (BriefMember briefMember : briefMemberList) {
            User user = userMapper.getUserByChuangNum(briefMember.getChuangNum());
            RecruitProjectMember recruitProjectMember = new RecruitProjectMember(briefMember.getChuangNum(), user.getName(), user.getTelephone(), user.getQQ(), user.getWechatNum(), user.getMajor(), briefMember.getWork());
            recruitProjectMembers.add(recruitProjectMember);
        }
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(id);
        GetRecruitProjectResponse getRecruitProjectResponse = new GetRecruitProjectResponse(recruitProject, recruitProjectMembers);
        return getRecruitProjectResponse;
    }

    @Override
    public Page<BriefRecruitProject> getBriefRecruitProjectList(GetBriefRecruitProjectListRequest request) {
        if (request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "type desc, state_update_time desc");
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRList();
        return new Page(new PageInfo<>(briefRecruitProjectList));
    }

    @Override
    public Page<BriefRecruitProject> getBriefRecruitProjectListByType(GetBriefRecruitProjectListRequest request) {
        if (request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), "state_update_time desc");
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRListByRecruitType(request.getType());
        return new Page(new PageInfo<>(briefRecruitProjectList));
    }

    @Override
    public List<BriefRecruitProject> getHomepageBriefRecruitProjectList() {
        List<BriefRecruitProject> briefRecruitProjectList = recruitProjectMapper.getAllBriefRListByRecruitType(1);
      
        if(briefRecruitProjectList.size()<3){
            List<BriefRecruitProject> briefRecruitProjectList1 = recruitProjectMapper.getAllBriefRListByRecruitType(0);
            for(int i = 0; briefRecruitProjectList.size() < 3; i++){
                briefRecruitProjectList.add(briefRecruitProjectList1.get(i));
            }
            return briefRecruitProjectList;
        }
        else return briefRecruitProjectList.subList(0,3);
    }

    @Override
    public List<BriefRecruitProject> getHomepageMyProjectList(String userChuangNum) {
  List<Long> list= memberMapper.getTeamByChuangNum(userChuangNum);
  List<BriefRecruitProject> briefRecruitProjectList=new ArrayList<>();
  for (Long e:list){
      RecruitProject recruitProject=recruitProjectMapper.getRecruitProjectById(e);
      briefRecruitProjectList.add(new BriefRecruitProject(recruitProject.getId(),recruitProject.getName(),recruitProject.getTag1(),recruitProject.getTag2(),recruitProject.getTag3(),recruitProject.getBriefDemand(),recruitProject.getStatus(), recruitProject.getRecruitType(), recruitProject.getIntroduction(),recruitProject.getContactInformation()));
  }
  if(briefRecruitProjectList.size()<3){
      return briefRecruitProjectList;
  }
  else return briefRecruitProjectList.subList(0,3);
    }

    @Override
    public Long createProject(CreateProjectRequest creatProjectRequest, String userChuangNum) {
        String stateUpdateTime = timeUtil.getCurrentTimestamp();
        Integer status = 0;
        String chuangNum = creatProjectRequest.getCaptainChuangNum();
        if (chuangNum == null) chuangNum = userChuangNum;
        User user = userMapper.getUserByChuangNum(chuangNum);
        RecruitProject recruitProject = new RecruitProject(null, creatProjectRequest.getName(), chuangNum, userMapper.getUserByChuangNum(chuangNum).getName(), creatProjectRequest.getInstitute(), creatProjectRequest.getIntroduction(), creatProjectRequest.getBriefDemand(), creatProjectRequest.getTeacherName(), creatProjectRequest.getTeacherApartment(), creatProjectRequest.getTeacherRank(), creatProjectRequest.getPlanStartTime(), creatProjectRequest.getPlanEndTime(), timeUtil.getCurrentTimestamp(), timeUtil.getCurrentTimestamp(), null, stateUpdateTime, creatProjectRequest.getDemand(), status, creatProjectRequest.getRecruitNum(), creatProjectRequest.getTag1(), creatProjectRequest.getTag2(), creatProjectRequest.getTag3(), 1L, null,0,creatProjectRequest.getTeacherPersonalHomepage(),user.getTelephone());
        recruitProjectMapper.newRecruitProject(recruitProject);
        memberMapper.insertMember(recruitProject.getId(), MemberTypeEnum.CAPTAIN.getDescription(), 0, chuangNum, "??????");
        return recruitProject.getId();
    }

    @Override
    public Long createTeacherProject(CreateProjectRequest createProjectRequest, String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);
        if (user.getType() != 1) throw new CommonException(CommonErrorCode.USER_NOT_TEACHER);

        String statusUpdateTime = TimeUtil.getCurrentTimestamp();
        Integer status = 0;
        RecruitProject recruitProject = new RecruitProject(null, createProjectRequest.getName(), userChuangNum, null, createProjectRequest.getInstitute(),createProjectRequest.getIntroduction(), createProjectRequest.getBriefDemand(), createProjectRequest.getTeacherName(), createProjectRequest.getTeacherApartment(), createProjectRequest.getTeacherRank(), createProjectRequest.getPlanStartTime(),createProjectRequest.getPlanEndTime(), TimeUtil.getCurrentTimestamp(), TimeUtil.getCurrentTimestamp(), null, statusUpdateTime, createProjectRequest.getDemand(), status, createProjectRequest.getRecruitNum(), createProjectRequest.getTag1(), createProjectRequest.getTag2(), createProjectRequest.getTag3(), 0L, null, 1,createProjectRequest.getTeacherPersonalHomepage(),user.getTelephone());
        recruitProjectMapper.newRecruitProject(recruitProject);
        return recruitProject.getId();
    }

    @Override
    public void applyForDisplayProject(ApplyForDisplayProjectRequest request) {
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(request.getRecruitProjectId());
        String uploadTime = timeUtil.getCurrentTimestamp();
        if (recruitProject.getStatus() == -1) {
            DisplayProject displayProject = new DisplayProject(null, request.getYear(), request.getInstitute(), request.getName(), request.getCaptainName(), request.getTeacherOneName(), request.getTeacherOneApartment(), request.getTeacherOneRank(), request.getTeacherOneStudy(), request.getTeacherTwoName(), request.getTeacherTwoApartment(), request.getTeacherTwoRank(), request.getTeacherTwoStudy(), uploadTime, request.getIntroduction(), request.getType(), request.getMajor(), request.getNumber(), request.getAward(), request.getInnovation(), null, null, request.getPaper(), request.getMemberOneName(), request.getMemberOneGrade(), request.getMemberOneMajor(), request.getMemberTwoName(), request.getMemberTwoGrade(), request.getMemberTwoMajor(), request.getMemberThreeName(), request.getMemberThreeGrade(), request.getMemberThreeMajor(), request.getMemberFourName(), request.getMemberFourGrade(), request.getMemberFourMajor(), request.getMemberFiveName(), request.getMemberFiveGrade(), request.getMemberFiveMajor(), request.getFile(), request.getFileName(), request.getFileTwo(), request.getFileTwoName(), 0,0L,0L,request.getDisplayType());
            displayProjectMapper.insert(displayProject);
            messageMapper.applyForDisplayProject(MessageTypeEnum.Admin.getDescription(), recruitProject.getName(),recruitProject.getId(),0,timeUtil.getCurrentTimestamp(),null,0,recruitProject.getCaptainChuangNum(), userMapper.getNicknameByChuangNum(recruitProjectMapper.getCaptianChuangNumByProjectId(recruitProject.getId())));
        }
    }

    @Override
    public void updateProjectStatusById(UpdateProjectStatusRequest updateProjectStatusRequest) {
        recruitProjectMapper.updateProjectStatusById(updateProjectStatusRequest.getProjectId(), updateProjectStatusRequest.getNewStatus(), timeUtil.getCurrentTimestamp());
    }

    @Override
    public Integer getApplications(Long projectId) {
        List<Message> messageList = messageMapper.getApplication(MessageTypeEnum.APPLICATION.getDescription(), projectId);
        return messageList.size();
    }

    @Override
    public Page<BriefApplication> getBriefApplicationList(GetBriefApplicationListRequest request) {
        List<Message> messageList = messageMapper.getApplication(MessageTypeEnum.APPLICATION.getDescription(), request.getId());
        List<BriefApplication> briefApplications = new ArrayList<>();
        for (Message message : messageList) {
            BriefUser user = userMapper.getUserInformationByChuangNum(message.getMemberChuangNum());
            BriefApplication briefApplication = new BriefApplication(message.getId(), message.getMemberChuangNum(), user.getName(), user.getMajor(), user.getGrade(), user.getTelephone(), user.getQQ(), message.getIsRead(), message.getStatusUpdateTime(), message.getStatus());
            briefApplications.add(briefApplication);
        }
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        return new Page(new PageInfo<>(briefApplications));
    }

    @Override
    public Page<BriefRecruitProject> searchRecruitProject(SearchRequest searchRequest) {
        Example example = new Example(RecruitProject.class);
//        Example.Criteria statusCriteria = example.createCriteria();
//        statusCriteria.andEqualTo("status", 0);

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
            for (BriefUserName ele : briefUserNameList) {
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

        Example.Criteria tagCriteria = example.createCriteria();
        tagCriteria.andEqualTo("recruitType",searchRequest.getRecruitType());

        example.and();
//        example.orderBy("id").desc();
        example.setOrderByClause("`status` desc,`id` desc");
//
//        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
//                searchRequest.getPageParam().getPageSize(),"state_update_time desc");

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize());
        List<RecruitProject> recruitProjectList = recruitProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(recruitProjectList));

        ArrayList<BriefRecruitProject> searchResponseArrayList = new ArrayList<>();
        for (RecruitProject ele : recruitProjectList) {
            searchResponseArrayList.add(new BriefRecruitProject(ele.getId(), ele.getName(), ele.getTag1(), ele.getTag2(), ele.getTag3(), ele.getBriefDemand(), ele.getStatus(),ele.getRecruitType(),ele.getIntroduction(),ele.getContactInformation()));
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

    @Override
    public String uploadDemandRTF(MultipartFile multipartFile) throws CommonException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = multipartFile.getOriginalFilename();
//            String name=new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));
            String time = TimeUtil.getCurrentTimestamp();
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "recruitProjectDemandRTF" + time + "." + name, multipartFile.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "recruitProjectDemandRTF" + time + "." + name).toString();
//            res = URLDecoder.decode(res, "utf-8");

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }
        return res;
    }

    @Override
    public String uploadIntroductionRTF(MultipartFile multipartFile) throws CommonException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = multipartFile.getOriginalFilename();
//            String name=new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));
            String time = TimeUtil.getCurrentTimestamp();
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "recruitProjectIntroductionRTF" + time + "." + name, multipartFile.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "recruitProjectIntroductionRTF" + time + "." + name).toString();
//            res = URLDecoder.decode(res, "utf-8");


        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }
        return res;
    }

    @Override
    public void updateDemandRTF(Long recruitProjectId, String demand) {
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(recruitProjectId);
        recruitProject.setDemand(demand);
        recruitProjectMapper.updateByPrimaryKey(recruitProject);
    }

    @Override
    public void updateIntroductionRTF(Long recruitProjectId, String introduction) {
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(recruitProjectId);
        recruitProject.setDemand(introduction);
        recruitProjectMapper.updateByPrimaryKey(recruitProject);
    }
}