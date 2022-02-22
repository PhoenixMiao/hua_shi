package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.mapper.ColletionMapper;
import com.phoenix.huashi.mapper.DisplayProjectMapper;
import com.phoenix.huashi.mapper.LikesMapper;
import com.phoenix.huashi.util.RedisUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ScheduledTasks {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DisplayProjectMapper displayProjectMapper;

    //@Scheduled(cron = "0/30 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0 */1 * ?")
    public void likes2Mysql() {
        Set<String> keySet = redisUtils.keys("likes*");
        for (String key : keySet) {
            displayProjectMapper.setLikeNumber(Long.valueOf(String.valueOf(redisUtils.get(key))),Long.valueOf(key.substring(5)));
        }
        redisUtils.clear("likes*");
    }

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0 */1 * ?")
    public void collection2Mysql() {
        Set<String> keySet = redisUtils.keys("collections*");
        for (String key : keySet) {
            displayProjectMapper.setCollectionNumber(Long.valueOf(String.valueOf(redisUtils.get(key))),Long.valueOf(key.substring(11)));
        }
        redisUtils.clear("collections*");
    }
}
