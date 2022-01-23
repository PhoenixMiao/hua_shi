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
        @Insert("INSERT INTO collection VALUE(null,#{recruitProjectId},null,#{collectTime},#{userId})")
        void addToCollection(
                @Param("userId") Long userId,
                @Param("recruitProjectId") Long recruitProjectId,
                @Param("collectTime") String collectTime);

        @Delete("DELETE FROM collection WHERE id=#{id};")
        void cancelCollection(@Param("id")Long id);

        @Select("SELECT * FROM collection WHERE userId = #{userId}")
        List<Collection> getCollectionList(@Param("userId")Long userId);


}
