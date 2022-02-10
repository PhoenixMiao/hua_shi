package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.service.LikeService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Api("点赞相关操作")
@RestController
@RequestMapping("/like")
@Validated
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private SessionUtils sessionUtils;
   @Auth
    @GetMapping("/{projectId}")
    @ApiOperation(value = "点赞",response = String.class)
    public Object giveLike( @PathVariable("projectId") Long projectId){
        likeService.like(projectId,sessionUtils.getUserChuangNum());
        return "操作成功";
    }
    @GetMapping("/cancel/{id}")
    @ApiOperation(value = "取消点赞",response = String.class)
    public Object cancelLike(@PathVariable("id")Long id){
        likeService.cancelLike(id);
        return "操作成功";
    }
}
