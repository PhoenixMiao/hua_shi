package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.SearchResponse;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.mapper.*;
import com.phoenix.huashi.service.SearchService;
import com.phoenix.huashi.util.SessionUtils;
import com.phoenix.huashi.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Autowired
    private UserMapper userMapper;

    //https://blog.csdn.net/luluyo/article/details/81708833
    @Override
    public Page<SearchResponse> search(SearchRequest searchRequest) {
        if (searchRequest.getType() == 1) return searchNotification(searchRequest);
        if (searchRequest.getType() == 2) return searchDisplayProject(searchRequest);
        if (searchRequest.getType() == 3) return searchRecruitProject(searchRequest);
        return null;
    }


    private Page<SearchResponse> searchNotification(SearchRequest searchRequest) {
        Example example = new Example(Notification.class);
        //example.selectProperties("id","title","source","publishDate");

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("title", "%"+searchRequest.getName()+"%");
            example.and(nameCriteria);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<Notification> notificationList = notificationMapper.selectByExample(example);
        Page page = new Page(new PageInfo(notificationList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (Notification ele : notificationList) {
            searchResponseArrayList.add(new SearchResponse(1, ele.getId(), ele.getTitle(),ele.getType(), ele.getSource(), ele.getPublish_date()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

        /*
        List<BriefNotification> collect = notificationList.stream()
                .map(i -> notificationMapper.getBriefNotificationById(i.getId()))
                .collect(Collectors.toList());
        return new Page<>(searchRequest.getPageParam(),page.getTotal(),page.getPages(),collect);
         */
    }

    private Page<SearchResponse> searchDisplayProject(SearchRequest searchRequest) {
        Example example = new Example(DisplayProject.class);

        if (!StringUtils.isEmpty(searchRequest.getDepartment())) {
            Example.Criteria departmentCriteria = example.createCriteria();
            departmentCriteria.orLike("institute", "%"+searchRequest.getDepartment()+"%");
            departmentCriteria.orLike("major", "%"+searchRequest.getDepartment()+"%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("name", "%"+searchRequest.getName()+"%");
            example.and(nameCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getCaptain())) {
            List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchRequest.getCaptain());
            Example.Criteria captainCriteria = example.createCriteria();
            for(BriefUserName ele:briefUserNameList)
                captainCriteria.orEqualTo("principalChuangNum",ele.getChuangNum());
            example.and(captainCriteria);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<DisplayProject> displayProjectList = displayProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(displayProjectList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (DisplayProject ele : displayProjectList) {
            searchResponseArrayList.add(new SearchResponse(2, ele.getId(), ele.getName(), ele.getType(), userMapper.getUserByChuangNum(ele.getPrincipalChuangNum()).getName(), ele.getInstitute()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }

    private Page<SearchResponse> searchRecruitProject(SearchRequest searchRequest) {
        Example example = new Example(RecruitProject.class);
        Example.Criteria statusCriteria = example.createCriteria();
        statusCriteria.andEqualTo("status",0);

        if (!StringUtils.isEmpty(searchRequest.getDepartment())) {
            Example.Criteria departmentCriteria = example.createCriteria();
            departmentCriteria.orLike("institute", "%"+searchRequest.getDepartment()+"%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("name", "%"+searchRequest.getName()+"%");
            example.and(nameCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getCaptain())) {
            List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchRequest.getCaptain());
            Example.Criteria captainCriteria = example.createCriteria();
            for(BriefUserName ele:briefUserNameList)
                captainCriteria.orEqualTo("captain_Chuang_Num",ele.getChuangNum());
            example.and(captainCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getTag())) {
            Example.Criteria tagCriteria = example.createCriteria();
            tagCriteria.orLike("tag1", "%"+searchRequest.getTag()+"%");
            tagCriteria.orLike("tag2", "%"+searchRequest.getTag()+"%");
            tagCriteria.orLike("tag3", "%"+searchRequest.getTag()+"%");
            example.and(tagCriteria);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<RecruitProject> recruitProjectList = recruitProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(recruitProjectList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (RecruitProject ele : recruitProjectList) {
            searchResponseArrayList.add(new SearchResponse(3, ele.getId(), ele.getName(), ele.getTag1(), userMapper.getUserByChuangNum(ele.getCaptain_chuang_num()).getName(), userMapper.getUserByChuangNum(ele.getCaptain_chuang_num()).getDepartment()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }
}
