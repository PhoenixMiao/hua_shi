package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.service.CollectionService;
import com.phoenix.huashi.util.TimeUtil;
import com.phoenix.huashi.mapper.ColletionMapper;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.entity.Collection;
import com.phoenix.huashi.controller.request.GetCollectionRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private ColletionMapper collectionMapper;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Override
    public void addToCollection(Long recruitProjectId, Long userId) {
        String createTime = timeUtil.getCurrentTimestamp();

        collectionMapper.addToCollection(recruitProjectId, userId, createTime);
    }

    @Override
    public void cancelCollection(Long id) {
        collectionMapper.cancelCollection(id);
    }

    @Override
    public Page<BriefCollection> getBriefCollectionList(GetCollectionRequest getCollectionRequest, Long userId) {

        List<Collection> collectionList = collectionMapper.getCollectionList(userId);
        List<BriefCollection> briefCollectionList = new ArrayList<>();
        for (Collection e : collectionList) {
            DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(e.getRecruitProjectId());

            BriefCollection briefCollection = new BriefCollection(displayProject.getName(), displayProject.getUserId(), displayProject.getType(), displayProject.getStatus());
            briefCollectionList.add(briefCollection);
        }


        PageParam pageParam = getCollectionRequest.getPageParam();
        //pageParam.setOrderBy("create_time");
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        return new Page(new PageInfo<>(briefCollectionList));
    }

}
