package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.dto.notification.BriefNotification;
import com.phoenix.huashi.entity.Collection;
import com.phoenix.huashi.entity.Notification;
import com.phoenix.huashi.mapper.NotificationMapper;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.phoenix.huashi.enums.CommodityTypeEnum;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;

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
    public List<BriefNotification> getHomepageNotification() {
        List<BriefNotification> briefNotificationList = notificationMapper.getALLBriefNotificationList();
        Collections.sort(briefNotificationList, new Comparator<BriefNotification>() {
            @Override
            public int compare(BriefNotification t1, BriefNotification t2) {
                Date d1= TimeUtil.parseToDate(t1.getPublishDate());
                Date d2=TimeUtil.parseToDate(t2.getPublishDate());
                if(d1.after(d2)){
                    return  -1;
                }
                else  return 1;
            }
        });
        if (briefNotificationList.size()<3){
            List<BriefNotification> list=new ArrayList<>();
            for(BriefNotification b:briefNotificationList){
                list.add(b);
            }
            return list;
        }
        else return briefNotificationList.subList(0,3);
    }

    @Override
    public void updateNotification() {
        String url = "http://www.cxcy.ecnu.edu.cn/18349/list.htm";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements listDiv = doc.getElementsByAttributeValue("target", "_blank");
            for (Element element : listDiv) {

                    // 取所有文本
                    // String ptext = text.text();
                Document doc1 = Jsoup.connect("http://www.cxcy.ecnu.edu.cn"+element.attr("href")).get();
                Elements list = doc1.getElementsByAttributeValue("class", "Article_PublishDate");
                for(Element e:list){
                  Notification notification= Notification.builder()
                          .title(element.attr("title"))
                          .url("http://www.cxcy.ecnu.edu.cn"+element.attr("href"))
                          .source("本科创新创业教育网")
                          .publishDate(e.text())
                          .type("创新创业训练计划")
                          .build();
notificationMapper.insert(notification);}

            }
        } catch (Exception e) {

          throw new CommonException(CommonErrorCode.UPDATE_FAIL)  ;
        }



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


