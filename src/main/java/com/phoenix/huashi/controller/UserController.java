package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.GetBriefUserNameListRequest;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.controller.request.GetTeamListRequest;
import com.phoenix.huashi.controller.request.UpdateUserByChuangNumRequest;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.service.MessageService;
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
    @ApiOperation(value = "登录", response = SessionData.class)
    @ApiImplicitParam(name = "code", value = "code", required = true, paramType = "path")
    public Object login(@NotBlank @PathVariable("code") String code) {

        return userService.login(code);

    }

    @Auth
    @GetMapping("/info")
    @ApiOperation(value = "查看用户信息", response = GetUserResponse.class)
    @ApiImplicitParam(name="userChuangNum",value="用户创赛号",required = true,paramType = "query",dataType = "String")
    public Object getUserByChuangNum(@NotNull @RequestParam("userChuangNum") String userChuangNum) {
        User user = userService.getUserByChuangNum(userChuangNum);
        return user;
    }


    @Auth
    @PostMapping("/update")
    @ApiOperation(value = "更新当前用户信息", response = String.class)
    public Object updateUserById(@NotNull @Valid @RequestBody UpdateUserByChuangNumRequest updateUserByChuangNumRequest) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        userService.updateUserByChuangNum(updateUserByChuangNumRequest, userChuangNum);
        return "操作成功";
    }

    @Auth
    @PostMapping("/userNameList")
    @ApiOperation(value = "根据姓名获取用户姓名创赛号列表", response = BriefUserName.class)
    public Object getBriefUserNameListByName(@NotNull @Valid @RequestBody GetBriefUserNameListRequest request) {
        return userService.searchBriefUserNameListByName(request);
    }

    @Auth
    @PostMapping("/team")
    @ApiOperation(value = "查看我的组队", response = BriefProjectInformation.class)
    public Object getBriefTeamList(@NotNull @Valid @RequestBody GetTeamListRequest request) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        return userService.getBriefTeamList(request, userChuangNum);
    }

}
