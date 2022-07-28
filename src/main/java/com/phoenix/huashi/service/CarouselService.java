package com.phoenix.huashi.service;

import com.phoenix.huashi.entity.Carousel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarouselService {

    String uploadFile(Long projectId, String fileName, Integer type,MultipartFile multipartFile);

    List<Carousel> getCarouselList(Integer number);
}
