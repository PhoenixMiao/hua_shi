package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.controller.request.InviteUserRequest;
import com.phoenix.huashi.controller.request.ReplyMessageRequest;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Message;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @Auth
    @GetMapping("/apply")
    @ApiOperation(value = "申请加入项目", response = String.class)
    @ApiImplicitParam(name="projectId",value="项目id",required = true,paramType = "query",dataType = "Long")
    public Result applyForProject(@NotNull @RequestParam("projectId") Long projectId) {
        try {
            messageService.applyForProject(sessionUtils.getUserChuangNum(), projectId);
            return Result.success("申请成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/invite")
    @ApiOperation(value = "邀请别人加入项目", response = String.class)
    public Result projectInvitation(@NotNull @Valid @RequestBody InviteUserRequest request) {
        try {
            messageService.projectInvitation(request, sessionUtils.getUserChuangNum());
            return Result.success("邀请成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/list")
    @ApiOperation(value = "获取消息列表", response = String.class)
    public Object getBriefMessageList(@NotNull @Valid @RequestBody GetMessageListReuqest request) {
        try {
            return Result.success(messageService.getBriefMessageList(request,sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("")
    @ApiOperation(value = "查看消息详情", response = Object.class)
    @ApiImplicitParam(name="messageId",value="消息id",required = true,paramType = "query",dataType = "Long")
    public Object getMessage(@NotNull @RequestParam("messageId") Long id) {
        try {
            return Result.success(messageService.getMessage(id,sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/reply")
    @ApiOperation(value = "处理消息", response = String.class)
    public Object replyMessage(@NotNull @Valid @RequestBody ReplyMessageRequest request) {
        try {
            return Result.success( messageService.replyMessage(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }
}
