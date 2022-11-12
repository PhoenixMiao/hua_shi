package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.Vote;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteMapper extends MyMapper<Vote>{
    @Select("SELECT * FROM vote")
    List<Vote> getVote();

    @Delete("DELETE FROM vote")
    void deleteVote();
}
