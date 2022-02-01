package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefListRequest;

import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;


import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class DisplayProjectServiceImpl implements DisplayProjectService {
    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private RedisUtils redisUtil;

    private static String LIKE_KEY(Long id){
        return "redisdemo:likes:" + id;
    }

    private Long getLikesFromRedis(Long id){
        Long likes;
        try{
            likes = (Long) redisUtil.get(LIKE_KEY(id));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return likes;
    }


    @Override
    public Long getLike(Long projectId){
        Long likes = getLikesFromRedis(projectId);
        if(likes != null)return likes;
        likes = Optional.ofNullable(displayProjectMapper.getLikes(projectId)).orElse(0L);
        redisUtil.set(LIKE_KEY(projectId),likes);
        return likes;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void likes2Mysql() {
        Long likes;
        try{
            likes = (Long)redisUtil.get("redisdemo:likes:1");
            if(likes != null){
                displayProjectMapper.giveLike(likes,1L);
            }
        }catch (Exception e){
        }
    }

    @Override
    public void giveLike(Long projectId)
    {
        Long likes = getLikesFromRedis(projectId);
        if(likes != null){
            redisUtil.set(LIKE_KEY(projectId),likes + 1);
        }else {
            likes = Optional.ofNullable(displayProjectMapper.getLikes(projectId)).orElse(0L);
            redisUtil.set(LIKE_KEY(projectId), likes + 1);
        }
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
