package com.phoenix.huashi.service.team.impl;

import com.phoenix.huashi.mapper.TeamMapper;
import com.phoenix.huashi.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public void addTeam()
    {
        teamMapper.addTeam();
    }

}
