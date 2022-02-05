package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("消息相关操作")
@RestController
@RequestMapping("/message")
@Validated
public class MessageController {
    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private MessageService messageService;
    // @Auth
    @GetMapping("/apply/{projectId}")
    @ApiOperation(value = "申请加入项目", response = String.class)
    public Object applyForProject(@PathVariable("projectId") Long projectId) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        messageService.applyForProject("zy",projectId);
        return "申请成功";
    }
    // @Auth
    @PostMapping("/invite")
    @ApiOperation(value = "邀请别人加入项目", response = String.class)
    public Object projectInvitation(@NotNull @Valid @RequestBody InviteUserRequest request) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        messageService.projectInvitation(request,"zy");
        return "邀请成功";
    }
    // @Auth
    @PostMapping("/list")
    @ApiOperation(value = "获取消息列表", response = String.class)
    public Object projectInvitation(@NotNull @Valid @RequestBody GetMessageListReuqest request) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        return messageService.getMessageList(request,"zy");
    }
}
