package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;

import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.GetDisplayProjectResponse;
import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.dto.member.BriefMember;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.dto.user.DisplayProjectMember;
import com.phoenix.huashi.entity.DisplayProject;


import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.enums.CommodityTypeEnum;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.CollectionService;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    private RedisUtils redisUtil;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;


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
            List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchRequest.getCaptain());
            Example.Criteria captainCriteria = example.createCriteria();
            for (BriefUserName ele : briefUserNameList)
                captainCriteria.orEqualTo("principalChuangNum", ele.getChuangNum());
            example.and(captainCriteria);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<DisplayProject> displayProjectList = displayProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(displayProjectList));

        ArrayList<BriefDisplayProject> searchResponseArrayList = new ArrayList<>();
        for (DisplayProject ele : displayProjectList) {
            searchResponseArrayList.add(new BriefDisplayProject(ele.getId(), ele.getName(), userMapper.getUserByChuangNum(ele.getCaptainChuangNum()).getName(), ele.getType(), ele.getInstitute()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }
}
