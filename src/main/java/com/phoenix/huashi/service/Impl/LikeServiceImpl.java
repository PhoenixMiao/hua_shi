package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.entity.Collection;
import com.phoenix.huashi.entity.Likes;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.service.LikeService;

import java.util.Optional;

import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DisplayProjectMapper displayProjectMapper;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private LikesMapper likesMapper;

    private static String LIKE_KEY(Long id) {
        return id.toString();
    }

    private Long getLikesFromRedis(Long id) {
        Long likes;
        try {
            likes = (Long) redisUtils.get(LIKE_KEY(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return likes;
    }


    @Override
    public Long getLikeNumber(Long projectId) {
        Long likes = getLikesFromRedis(projectId);
        if (likes != null) return likes;
        likes = Optional.ofNullable(displayProjectMapper.getLikes(projectId)).orElse(0L);
        redisUtils.set(LIKE_KEY(projectId), likes);
        return likes;
    }

    //    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0 */1 * ?")
    public void likes2Mysql() {
        Long likes;
        try {
            likes = (Long) redisUtils.get("redisdemo:likes:1");
            if (likes != null) {
                displayProjectMapper.giveLike(likes, 1L);
            }
        } catch (Exception e) {
        }
    }


    @Override
    public void like(Long projectId, String userChuangNum) {
        Long likes = getLikeNumber(projectId);
        redisUtils.set(LIKE_KEY(projectId), likes + 1);
        String createTime = timeUtil.getCurrentTimestamp();
        likesMapper.addToLikes(projectId, userChuangNum, createTime);
    }

    @Override
    public void cancelLike(Long id) {
        Likes like = likesMapper.getLikeById(id);
        Long projectId = like.getProjectId();
        Long likes = getLikeNumber(projectId);
        redisUtils.set(LIKE_KEY(projectId), likes - 1);
        likesMapper.cancelLike(id);
    }
}
