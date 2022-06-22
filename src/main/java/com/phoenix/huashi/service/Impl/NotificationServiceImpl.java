package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.mapper.NotificationMapper;
import com.phoenix.huashi.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.phoenix.huashi.enums.CommodityTypeEnum;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;


    @Override
    public Notification getNotificationById(Long id) {
        Notification notification = notificationMapper.getNotificationById(id);
        return notification;
    }

    @Override
    public Page<BriefNotification> getBriefNotificationList(@NotNull @RequestBody GetBriefProjectListRequest request) {
        if (request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());

        if (request.getType().equals(CommodityTypeEnum.INNOVATIONCOMPETITION.getName())) {
            List<BriefNotification> briefNotificationList = notificationMapper.getBriefNotificationList(CommodityTypeEnum.INNOVATIONCOMPETITION.getDescription());
            return new Page(new PageInfo<>(briefNotificationList));
        } else if (request.getType().equals(CommodityTypeEnum.ACADEMICCOMPETITION.getName())) {
            List<BriefNotification> briefNotificationList = notificationMapper.getBriefNotificationList(CommodityTypeEnum.ACADEMICCOMPETITION.getDescription());
            return new Page(new PageInfo<>(briefNotificationList));
        } else if (request.getType().equals(CommodityTypeEnum.ALL.getName())) {
            List<BriefNotification> briefNotificationList = notificationMapper.getALLBriefNotificationList();
            return new Page(new PageInfo<>(briefNotificationList));
        }
        return null;
    }

    @Override
    public Page<BriefNotification> searchNotification(SearchRequest searchRequest) {
        Example example = new Example(Notification.class);
        //example.selectProperties("id","title","source","publishDate");

        if (!StringUtils.isEmpty(searchRequest.getName())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("title", "%" + searchRequest.getName() + "%");
            example.and(nameCriteria);
        }
        example.orderBy("id").desc();

        PageHelper.startPage(searchRequest.getPageParam().getPageNum(),
                searchRequest.getPageParam().getPageSize(),
                searchRequest.getPageParam().getOrderBy());
        List<Notification> notificationList = notificationMapper.selectByExample(example);
        Page page = new Page(new PageInfo(notificationList));

        ArrayList<BriefNotification> searchResponseArrayList = new ArrayList<>();
        for (Notification ele : notificationList) {
            searchResponseArrayList.add(new BriefNotification(ele.getId(),ele.getTitle(),ele.getSource(),ele.getPublishDate()));
        }
        return new Page<>(searchRequest.getPageParam(), page.getTotal(), page.getPages(), searchResponseArrayList);

        /*
        List<BriefNotification> collect = notificationList.stream()
                .map(i -> notificationMapper.getBriefNotificationById(i.getId()))
                .collect(Collectors.toList());
        return new Page<>(searchRequest.getPageParam(),page.getTotal(),page.getPages(),collect);
         */
    }
}


