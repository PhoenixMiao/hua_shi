package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.Carousel;
import com.phoenix.huashi.entity.DisplayProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselMapper extends MyMapper<Carousel> {

    @Select("SELECT * FROM carousel ORDER BY upload_time LIMIT #{number}")
    List<Carousel> getCarouselList(@Param("number")Integer number);
}
