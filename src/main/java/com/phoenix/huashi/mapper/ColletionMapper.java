package com.phoenix.huashi.mapper;
import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ColletionMapper extends MyMapper<Collection> {
        @Insert("INSERT INTO  VALUE(null,#{recruitProjectId},#{chuangNum},#{collectTime})")
        void addToCollection(
                @Param("recruitProjectId")Long recruitProjectId,
                @Param ("chuangNum")String chuangNum,
                @Param("collectTime")String collectTime);

        @Delete("DELETE FROM collection WHERE id=#{id};")
        void cancelCollection(@Param("id")Long id);

        @Select("SELECT * FROM collection WHERE chuangNum = #{chuangNum}")
        List<Collection> getCollectionList(@Param("chuangNum")String chuangNum);

}
