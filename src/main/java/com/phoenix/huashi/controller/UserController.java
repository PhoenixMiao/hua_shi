package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.GetBriefUserNameListRequest;
import com.phoenix.huashi.controller.request.UpdateUserByIdRequest;
import com.phoenix.huashi.controller.response.GetUserByIdResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Api("用户相关操作")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

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

    @Auth
    @GetMapping("/info")
    @ApiOperation(value = "查看当前用户信息",response = GetUserByIdResponse.class)
    public Object getUserByIdResponse(){
        Long id = sessionUtils.getUserId();
        GetUserByIdResponse userResponse = userService.getUserById(id);
        return userResponse;
    }

@Auth
    @PostMapping("/info")
    @ApiOperation(value = "更新当前用户信息",response = String.class)
    public Object updateUserById(@NotNull @Valid @RequestBody UpdateUserByIdRequest updateUserByIdRequest){
        Long id = sessionUtils.getUserId();
        userService.updateUserById(updateUserByIdRequest,id);
        return "操作成功";
    }
@Auth
    @PostMapping("/userNameList")
    @ApiOperation(value = "根据姓名获取用户姓名创赛号列表", response = BriefUserName.class)
    public Object getBriefUserNameListByName(@NotNull@Valid @RequestBody GetBriefUserNameListRequest request) {
        return userService.searchBriefUserNameListByName(request);
    }
}
