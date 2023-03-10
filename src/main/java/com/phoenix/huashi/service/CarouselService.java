package com.phoenix.huashi.service;

import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.entity.Carousel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarouselService {

    String uploadFile(Long projectId, String fileName, Integer type,MultipartFile multipartFile);

    Page<Carousel> getAllCarouselList(GetListRequest getListRequest);

    List<Carousel> getCarouselList(Integer number);
}
