package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ColletionMapper extends MyMapper<Collection> {
    @Insert("INSERT INTO collection(id,project_id,chuang_num,collect_time) VALUE(null,#{project_id},#{chuang_num},#{collect_time})")
    void addToCollection(
            @Param("chuang_num") String chuangNum,
            @Param("project_id") Long projectId,
            @Param("collect_time") String collectTime);

    @Delete("DELETE FROM collection WHERE id=#{id};")
    void cancelCollection(@Param("id") Long id);

    @Select("SELECT * FROM collection WHERE chuang_num = #{chuang_num}")
    List<Collection> getCollectionList(@Param("chuang_num") String chuangNum);

    @Select("SELECT * FROM collection WHERE id = #{id}")
    Collection getCollectionById(@Param("id") Long id);


}
