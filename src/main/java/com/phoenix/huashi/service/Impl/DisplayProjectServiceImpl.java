package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefProjectListRequest;

import com.phoenix.huashi.dto.displayproject.BriefDisplayProject;

import com.phoenix.huashi.entity.DisplayProject;


import com.phoenix.huashi.enums.CommodityTypeEnum;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DisplayProjectServiceImpl implements DisplayProjectService {
    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    @Autowired
    private RedisUtils redisUtil;

    @Autowired
    private TimeUtil timeUtil;

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

    //    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0 */1 * ?")
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
    public void giveLike(Long projectId,String userChuangNum)
    {
        Long likes = getLikesFromRedis(projectId);
        if(likes != null){
            redisUtil.set(LIKE_KEY(projectId),likes + 1);
        }else {
            likes = Optional.ofNullable(displayProjectMapper.getLikes(projectId)).orElse(0L);
            redisUtil.set(LIKE_KEY(projectId), likes + 1);
        }
        likesMapper.addToLikes(projectId,userChuangNum,timeUtil.getCurrentTimestamp());
    }

    @Override
    public DisplayProject getDisplayProjectById(Long id){
        DisplayProject displayProject = displayProjectMapper.getDisplayProjectById(id);
        return displayProject;
    }

    @Override
    public Page<BriefDisplayProject> getBriefDisplayProjectList(GetBriefProjectListRequest request)
    {
        if(request == null) return null;
        List<BriefDisplayProject> briefDisplayProjectList= new ArrayList<>();
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize(),pageParam.getOrderBy());
        if(request.getType()== CommodityTypeEnum.ALL.getName()){
            List<DisplayProject> displayProjects=displayProjectMapper.getBriefDisplayProjectList();
            for(DisplayProject project:displayProjects){
                BriefDisplayProject briefDisplayProject=new BriefDisplayProject(project.getId(),project.getName(),project.getPrincipal_name(),project.getType(),project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        }
        else if(request.getType()==CommodityTypeEnum.ACADEMICCOMPETITION.getName()){
            List<DisplayProject> displayProjects=displayProjectMapper.getBriefDisplayProjectListByType(CommodityTypeEnum.ACADEMICCOMPETITION.getDescription());
            for(DisplayProject project:displayProjects){
                BriefDisplayProject briefDisplayProject=new BriefDisplayProject(project.getId(),project.getName(),project.getPrincipal_name(),project.getType(),project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        }
        else if(request.getType()==CommodityTypeEnum.INNOVATIONCOMPETITION.getName()){
            List<DisplayProject> displayProjects = displayProjectMapper.getBriefDisplayProjectListByType(CommodityTypeEnum.INNOVATIONCOMPETITION.getDescription());
            for(DisplayProject project:displayProjects){
                BriefDisplayProject briefDisplayProject=new BriefDisplayProject(project.getId(),project.getName(),project.getPrincipal_name(),project.getType(),project.getInstitute());
                briefDisplayProjectList.add(briefDisplayProject);
            }
        }
        return new Page(new PageInfo<>(briefDisplayProjectList));
    }
}
