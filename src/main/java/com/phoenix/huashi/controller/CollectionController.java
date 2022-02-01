package com.phoenix.huashi.controller;
import com.phoenix.huashi.controller.request.GetCollectionRequest;
import com.phoenix.huashi.controller.request.AddToCollectionRequest;
import com.phoenix.huashi.dto.collection.BriefCollection;
import com.phoenix.huashi.service.CollectionService;

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

    @Auth
    @PostMapping("/add")
    @ApiOperation(value = "收藏项目",response = String.class)
    public Object addToCollection(@NotNull @Valid @RequestBody AddToCollectionRequest addToCollectionRequest){
        Long userId = sessionUtils.getUserId();
        collectionService.addToCollection(addToCollectionRequest.getRecruitProjectId(),userId);
        return "操作成功";
    }

    @Auth
    @PostMapping("/cancel/{id}")
    @ApiOperation(value = "取消收藏",response = String.class)
    public Object cancelCollection(@PathVariable("id")Long id){
        collectionService.cancelCollection(id);
        return "操作成功";
    }

    @Auth
    @PostMapping("/list")
    @ApiOperation(value = "查看收藏夹",response = BriefCollection.class)
    public Object getBriefCollectionList(@NotNull @Valid @RequestBody GetCollectionRequest getCollectionRequest){
        Long userId = sessionUtils.getUserId();
        return collectionService.getBriefCollectionList(getCollectionRequest,userId);
    }
}
