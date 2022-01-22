package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
public class UserController {

    @Autowired
    private DisplayProjectService displayProjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;


    @GetMapping("/login/{code}")
    @ApiOperation(value = "登录",response = SessionData.class)
    @ApiImplicitParam(name = "code", value = "code", required = true, paramType = "path")
    public Object login(@NotBlank @PathVariable("code") String code){

        return userService.login(code);

    }

    @GetMapping("/like/{projectId}")
    @ApiOperation(value = "点赞",response = String.class)
    public Object giveLike(@NotBlank @PathVariable("projectId") Long projectId){
        displayProjectService.giveLike(projectId, sessionUtils.getUserId());
        return "操作成功";
    }

}
