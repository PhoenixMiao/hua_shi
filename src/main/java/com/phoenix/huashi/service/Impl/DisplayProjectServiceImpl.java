package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.ApplyForDisplayProjectRequest;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;

import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.GetDisplayProjectResponse;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.dto.user.DisplayProjectMember;
import com.phoenix.huashi.entity.*;

import static com.phoenix.huashi.common.CommonConstants.*;

import com.phoenix.huashi.enums.CommodityTypeEnum;
import com.phoenix.huashi.mapper.*;
import com.phoenix.huashi.service.CollectionService;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.AssertUtil;
import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.TimeUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.qcloud.cos.transfer.TransferManager;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DisplayProjectServiceImpl implements DisplayProjectService {
    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ColletionMapper colletionMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private TransferManager transferManager;

    @Autowired
    private COSClient cosClient;

    @Override
    public Integer judgeLikeOrCollect(Long displayProjectId, String userChuangNum) {
        Integer result = 0;
        Likes like = likesMapper.getLikeByProjectIdAndUserChuangNum(displayProjectId, userChuangNum);
        if (like != null) result = result + 1;
        Collection collection = colletionMapper.getCollectionByProjectIdAndUserChuangNum(displayProjectId, userChuangNum);
        if (collection != null) result = result + 2;
        return result;
    }


    @Override
    public DisplayProject getDisplayProjectById(Long id) {
        DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(id);
        displayProject.setLikes(likeService.getLikeNumber(id));
        displayProject.setCollections(collectionService.getCollectionNumber(id));
        return displayProject;
    }

    @Override
    public Page<BriefDisplayProject> getBriefDisplayProjectList(GetBriefProjectListRequest request) {
        if (request == null) return null;
        List<BriefDisplayProject> briefDisplayProjectList = new ArrayList<>();
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        if (request.getType().equals(CommodityTypeEnum.ALL.getName())) {
            List<DisplayProject> displayProjects = displayProjectMapper.getBriefDisplayProjectList();
            for (DisplayProject project : displayProjects) {
                BriefDisplayProject briefDisplayProject = new BriefDisplayProject(project.getId(), project.getName(), project.getCaptainName(), project.getType(), project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        } else if (request.getType().equals(CommodityTypeEnum.ACADEMICCOMPETITION.getName())) {
            List<DisplayProject> displayProjects = displayProjectMapper.getBriefDisplayProjectListByType(CommodityTypeEnum.ACADEMICCOMPETITION.getDescription());
            for (DisplayProject project : displayProjects) {
                BriefDisplayProject briefDisplayProject = new BriefDisplayProject(project.getId(), project.getName(), project.getCaptainName(), project.getType(), project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        } else if (request.getType().equals(CommodityTypeEnum.INNOVATIONCOMPETITION.getName())) {
            List<DisplayProject> displayProjects = displayProjectMapper.getBriefDisplayProjectListByType(CommodityTypeEnum.INNOVATIONCOMPETITION.getDescription());
            for (DisplayProject project : displayProjects) {
                BriefDisplayProject briefDisplayProject = new BriefDisplayProject(project.getId(), project.getName(), project.getCaptainName(), project.getType(), project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        }
        return new Page(new PageInfo<>(briefDisplayProjectList));
    }

    @Override
    public Page<BriefDisplayProject> searchDisplayProject(SearchRequest searchRequest) {
        Example example = new Example(DisplayProject.class);

        if (!StringUtils.isEmpty(searchRequest.getDepartment())) {
            Example.Criteria departmentCriteria = example.createCriteria();
            departmentCriteria.orLike("institute", "%" + searchRequest.getDepartment() + "%");
            departmentCriteria.orLike("major", "%" + searchRequest.getDepartment() + "%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("name", "%" + searchRequest.getName() + "%");
            example.and(nameCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getCaptain())) {
            Example.Criteria captainCriteria = example.createCriteria();
            captainCriteria.orLike("captainName", "%" + searchRequest.getCaptain() + "%");
            example.and(captainCriteria);
        }
        example.orderBy("id").desc();

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize());
        List<DisplayProject> displayProjectList = displayProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(displayProjectList));

        ArrayList<BriefDisplayProject> searchResponseArrayList = new ArrayList<>();
        for (DisplayProject ele : displayProjectList) {
            searchResponseArrayList.add(new BriefDisplayProject(ele.getId(), ele.getName(), ele.getCaptainName(), ele.getType(), ele.getInstitute()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }

    @Override
    public Long addDisplayProject(ApplyForDisplayProjectRequest applyForDisplayProjectRequest) {
        RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(applyForDisplayProjectRequest.getRecruitProjectId());
        if(recruitProject.getStatus()!=-1)throw new CommonException(CommonErrorCode.PROGRAM_UNDERWAY);
        DisplayProject displayProject = DisplayProject
                .builder()
                .name(applyForDisplayProjectRequest.getName())
                .year(applyForDisplayProjectRequest.getYear())
                .type(applyForDisplayProjectRequest.getType())
                .number(applyForDisplayProjectRequest.getNumber())
                .uploadTime(TimeUtil.getCurrentTimestamp())
                .paper(applyForDisplayProjectRequest.getPaper())
                .teacherOneApartment(applyForDisplayProjectRequest.getTeacherOneApartment())
                .teacherOneName(applyForDisplayProjectRequest.getTeacherOneName())
                .teacherOneRank(applyForDisplayProjectRequest.getTeacherOneRank())
                .teacherOneStudy(applyForDisplayProjectRequest.getTeacherOneStudy())
                .teacherTwoApartment(applyForDisplayProjectRequest.getTeacherTwoApartment())
                .teacherTwoName(applyForDisplayProjectRequest.getTeacherTwoName())
                .teacherTwoRank(applyForDisplayProjectRequest.getTeacherTwoRank())
                .teacherTwoStudy(applyForDisplayProjectRequest.getTeacherTwoStudy())
                .award(applyForDisplayProjectRequest.getAward())
                .captainName(applyForDisplayProjectRequest.getCaptainName())
                .innovation(applyForDisplayProjectRequest.getInnovation())
                .institute(applyForDisplayProjectRequest.getInstitute())
                .major(applyForDisplayProjectRequest.getMajor())
                .introduction(applyForDisplayProjectRequest.getIntroduction())
                .memberFiveGrade(applyForDisplayProjectRequest.getMemberFiveGrade())
                .memberFiveName(applyForDisplayProjectRequest.getMemberFiveName())
                .memberFiveMajor(applyForDisplayProjectRequest.getMemberFiveMajor())
                .memberFourGrade(applyForDisplayProjectRequest.getMemberFourGrade())
                .memberFourName(applyForDisplayProjectRequest.getMemberFourName())
                .memberFourMajor(applyForDisplayProjectRequest.getMemberFourMajor())
                .memberOneGrade(applyForDisplayProjectRequest.getMemberOneGrade())
                .memberOneName(applyForDisplayProjectRequest.getMemberOneName())
                .memberOneMajor(applyForDisplayProjectRequest.getMemberOneMajor())
                .memberThreeGrade(applyForDisplayProjectRequest.getMemberThreeGrade())
                .memberThreeName(applyForDisplayProjectRequest.getMemberThreeName())
                .memberThreeMajor(applyForDisplayProjectRequest.getMemberThreeMajor())
                .memberTwoGrade(applyForDisplayProjectRequest.getMemberTwoGrade())
                .memberTwoName(applyForDisplayProjectRequest.getMemberTwoName())
                .memberTwoMajor(applyForDisplayProjectRequest.getMemberTwoMajor())
                .build();
        displayProjectMapper.insert(displayProject);
        Long displayProjectId = displayProject.getId();
        recruitProject.setDisplayId(displayProjectId);
        recruitProjectMapper.updateByPrimaryKeySelective(recruitProject);
        return displayProjectId;
    }

    @Override
    public String uploadFile(Long displayProjectId,  String fileName, MultipartFile multipartFile)throws CommonException {
        DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(displayProjectId);
        if(displayProject.getFile()!=null&& displayProject.getFile2()!=null)throw new CommonException(CommonErrorCode.EXCEED_MAX_NUMBER);

//                cosClient.deleteObject(COS_BUCKET_NAME,file.substring(file.indexOf(displayProject.getNumber())));


        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = multipartFile.getOriginalFilename();
//            String name=new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name,CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first=name.substring(0,name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));

            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, displayProject.getNumber() + "." + name, multipartFile.getInputStream(), objectMetadata);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res =  cosClient.getObjectUrl(COS_BUCKET_NAME,displayProject.getNumber() + "." +name).toString();
//            res = URLDecoder.decode(res, "utf-8");
            if(displayProject.getFile()==null){
                displayProject.setFile(res);
                displayProject.setFileName(fileName);
            }
            else if(displayProject.getFile2()==null){
                displayProject.setFile2(res);
                displayProject.setFile2Name(fileName);
            }

            displayProjectMapper.updateByPrimaryKeySelective(displayProject);

        } catch (Exception e){
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager

        return res;
    }


}
