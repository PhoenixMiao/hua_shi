package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.service.DisplayProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DisplayProjectServiceImpl implements DisplayProjectService {
    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Override
    public void giveLike(Long projectId,Long userId)
    {
        Date date = new Date( );
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String time=dateFormat.format(date);
        likesMapper.addToLikes(projectId,userId,time);
        Long likesNumber = displayProjectMapper.getLikes(projectId);
        displayProjectMapper.giveLike(likesNumber+1,projectId);
    }
}
