package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Likes;
import com.phoenix.huashi.service.CollectionService;
import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.TimeUtil;
import com.phoenix.huashi.mapper.ColletionMapper;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.entity.Collection;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private ColletionMapper collectionMapper;

    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static String COLLECTION_KEY(Long id) {
        return "collections"+id;
    }

    private Long getCollectionsFromRedis(Long id) {
        Long collections;
        try {
            collections = (Long) redisUtils.get(COLLECTION_KEY(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return collections;
    }

    @Override
    public Long getCollectionNumber(Long projectId) {
        Long collections = getCollectionsFromRedis(projectId);
        if (collections != null) return collections;
        collections = displayProjectMapper.getCollections(projectId);
        if (collections == null) {
            collections = 0L;
        }
        redisUtils.set(COLLECTION_KEY(projectId), collections);
        return collections;
    }


    @Override
    public void addToCollection(Long projectId, String userChuangNum) {
        Collection collection = collectionMapper.getCollectionByProjectIdAndUserChuangNum(projectId,userChuangNum);
        if(collection!=null) return ;
        Long collections = getCollectionNumber(projectId);
        redisUtils.set(COLLECTION_KEY(projectId), collections + 1);
        String createTime = timeUtil.getCurrentTimestamp();
        collectionMapper.addToCollection(userChuangNum, projectId, createTime);
        DisplayProject displayProject= displayProjectMapper.getDisplayProjectById(projectId);
        displayProject.setCollections(collections+1);
        displayProject.setHeat(displayProject.getHeat()+1);
        displayProjectMapper.updateByPrimaryKey(displayProject);
    }

    @Override
    public void cancelCollection(Long projectId,String userChuangNum) {
        Collection collection = collectionMapper.getCollectionByProjectIdAndUserChuangNum(projectId,userChuangNum);
        Long collections = getCollectionNumber(projectId);
        if (collections != null) {
            redisUtils.set(COLLECTION_KEY(projectId), collections - 1);
        } else {
            collections = Optional.ofNullable(displayProjectMapper.getCollections(projectId)).orElse(0L);
            redisUtils.set(COLLECTION_KEY(projectId), collections - 1);
        }
        collectionMapper.cancelCollection(collection.getId());
        DisplayProject displayProject= displayProjectMapper.getDisplayProjectById(projectId);
        displayProject.setCollections(collections-1);
        displayProject.setHeat(displayProject.getHeat()-1);
        displayProjectMapper.updateByPrimaryKey(displayProject);
    }

    @Override
    public Page<BriefCollection> getBriefCollectionList(GetListRequest getListRequest, String userChuangNum) {

        List<Collection> collectionList = collectionMapper.getCollectionList(userChuangNum);
        List<BriefCollection> briefCollectionList = new ArrayList<>();
        for (Collection e : collectionList) {
            DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(e.getProjectId());
            BriefCollection briefCollection = new BriefCollection(displayProject.getId(), displayProject.getName(), displayProject.getCaptainName(), displayProject.getType(), displayProject.getInstitute());
            briefCollectionList.add(briefCollection);
        }
        PageParam pageParam = getListRequest.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        return new Page(new PageInfo<>(briefCollectionList));
    }

}
