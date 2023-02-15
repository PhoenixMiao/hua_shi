package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.dto.news.BriefNews;
import com.phoenix.huashi.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper extends MyMapper<News> {
    @Select("SELECT * FROM news WHERE id=#{id}")
    News getNewsById(@Param("id") Long id);

    @Select("SELECT id,title,publish_date,picture,year,date FROM news")
    List<BriefNews> getBriefNewsList();

    @Insert("INSERT INTO news(title, publish_date, picture) VALUE (#{title},#{publishDate},#{picture})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void newNews(News news);
}
