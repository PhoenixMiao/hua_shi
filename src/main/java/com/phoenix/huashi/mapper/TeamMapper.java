package com.phoenix.huashi.mapper;

import com.phoenix.huashi.MyMapper;
import com.phoenix.huashi.entity.Team;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeamMapper extends MyMapper<Team> {

    @Insert("INSERT INTO team VALUES (null,null,1,null,null)")
    void addTeam();
}
