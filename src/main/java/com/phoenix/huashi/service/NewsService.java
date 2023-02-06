package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.AddNewsRequest;
import com.phoenix.huashi.controller.request.GetBriefNewsListRequest;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.dto.news.BriefNews;
import com.phoenix.huashi.entity.News;
import org.springframework.web.multipart.MultipartFile;


public interface NewsService {

    News getNewsById(Long id);

    Page<BriefNews> getBriefNewsList(GetBriefNewsListRequest request);

    Page<BriefNews> searchNews(SearchRequest searchRequest);

    String uploadContentRTF(Long newsId, MultipartFile file);

    Long addNews(AddNewsRequest request);

    String uploadPicture(Long newsId, MultipartFile file);



}
