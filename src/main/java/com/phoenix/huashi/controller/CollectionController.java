package com.phoenix.huashi.controller;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.service.CollectionService;

import com.phoenix.huashi.util.RedisUtils;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.phoenix.huashi.annotation.Auth;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Api("收藏相关操作")
@RestController
@RequestMapping("/collection")
@Validated
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RedisUtils redisUtils;
//    @Auth
    @GetMapping("/add/{projectId}")
    @ApiOperation(value = "收藏项目",response = String.class)
    public Object addToCollection(@NotNull @PathVariable("projectId") Long projectId){
        String userChuangNum = sessionUtils.getUserChuangNum();
        collectionService.addToCollection(projectId,userChuangNum);
        return "收藏成功";
    }

   // @Auth
    @GetMapping("/cancel/{id}")
    @ApiOperation(value = "取消收藏",response = String.class)
    public Object cancelCollection(@PathVariable("id")Long id){
        collectionService.cancelCollection(id);
        return "操作成功";
    }

//    @Auth
    @PostMapping("/list")
    @ApiOperation(value = "查看收藏夹",response = BriefCollection.class)
    public Object getBriefCollectionList(@NotNull @Valid @RequestBody GetListRequest getListRequest){
        String userChuangNum = sessionUtils.getUserChuangNum();
        return collectionService.getBriefCollectionList(getListRequest,userChuangNum);
    }

}
