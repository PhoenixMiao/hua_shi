package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.SearchResponse;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.SearchRecord;
import com.phoenix.huashi.mapper.*;
import com.phoenix.huashi.service.SearchService;
import com.phoenix.huashi.util.SessionUtils;
import com.phoenix.huashi.util.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SearchRecordMapper searchRecordMapper;

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

        if (!StringUtils.isEmpty(searchRequest.getDepartment())) {
            Example.Criteria departmentCriteria = example.createCriteria();
            departmentCriteria.orLike("source", "%"+searchRequest.getDepartment()+"%");
            departmentCriteria.orLike("title", "%"+searchRequest.getDepartment()+"%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getContents())) {
            Example.Criteria contentsCriteria = example.createCriteria();
            contentsCriteria.orLike("title", "%"+searchRequest.getContents()+"%");
            contentsCriteria.orLike("content", "%"+searchRequest.getContents()+"%");
            example.and(contentsCriteria);

            SearchRecord searchRecord = SearchRecord.builder()
                    .user_id(sessionUtils.getUserId())
                    .contents(searchRequest.getContents())
                    .create_time(TimeUtil.getCurrentTimestamp())
                    .build();
            searchRecordMapper.insert(searchRecord);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<Notification> notificationList = notificationMapper.selectByExample(example);
        Page page = new Page(new PageInfo(notificationList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (Notification ele : notificationList) {
            searchResponseArrayList.add(new SearchResponse(1, ele.getId(), ele.getTitle(),ele.getType(), ele.getSource(), ele.getPublishDate()));
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

        if (!StringUtils.isEmpty(searchRequest.getContents())) {
            Example.Criteria contentsCriteria = example.createCriteria();
            contentsCriteria.orLike("name", "%"+searchRequest.getContents()+"%");
            contentsCriteria.orLike("introduction", "%"+searchRequest.getContents()+"%");
            example.and(contentsCriteria);

            SearchRecord searchRecord = SearchRecord.builder()
                    .user_id(sessionUtils.getUserId())
                    .contents(searchRequest.getContents())
                    .create_time(TimeUtil.getCurrentTimestamp())
                    .build();
            searchRecordMapper.insert(searchRecord);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<DisplayProject> displayProjectList = displayProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(displayProjectList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (DisplayProject ele : displayProjectList) {
            searchResponseArrayList.add(new SearchResponse(1, ele.getId(), ele.getName(), ele.getType(), userMapper.getUserById(ele.getUserId()).getName(), ele.getInstitute()));
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
            //departmentCriteria.orLike("major", "%"+searchRequest.getDepartment()+"%");
            example.and(departmentCriteria);
        }

        if (!StringUtils.isEmpty(searchRequest.getContents())) {
            Example.Criteria contentsCriteria = example.createCriteria();
            contentsCriteria.orLike("name", "%"+searchRequest.getContents()+"%");
            contentsCriteria.orLike("introduction", "%"+searchRequest.getContents()+"%");
            example.and(contentsCriteria);

            SearchRecord searchRecord = SearchRecord.builder()
                    .user_id(sessionUtils.getUserId())
                    .contents(searchRequest.getContents())
                    .create_time(TimeUtil.getCurrentTimestamp())
                    .build();
            searchRecordMapper.insert(searchRecord);
        }

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<RecruitProject> recruitProjectList = recruitProjectMapper.selectByExample(example);
        Page page = new Page(new PageInfo(recruitProjectList));

        ArrayList<SearchResponse> searchResponseArrayList = new ArrayList<>();
        for (RecruitProject ele : recruitProjectList) {
            searchResponseArrayList.add(new SearchResponse(1, ele.getId(), ele.getName(), ele.getTag1(), userMapper.getUserByChuangNum(ele.getCaptainChuangNum()).getName(), userMapper.getUserByChuangNum(ele.getCaptainChuangNum()).getDepartment()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

    }
}
