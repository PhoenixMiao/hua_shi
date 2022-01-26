package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.Request.GetBriefListRequest;

import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;


import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.service.DisplayProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @Override
    public DisplayProject getDisplayProjectById(Long id, Long userId){
        DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(id);

        return displayProject;
    }
    @Override
    public Page<BriefDisplayProject> getBriefDisplayProjectList(@NotNull @RequestBody GetBriefListRequest request)
    { if(request == null) return null;
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize(),pageParam.getOrderBy());


            List<BriefDisplayProject> briefDisplayProjectList = displayProjectMapper.getBriefDisplayProjectList();
            return new Page(new PageInfo<>(briefDisplayProjectList));

        }
}
