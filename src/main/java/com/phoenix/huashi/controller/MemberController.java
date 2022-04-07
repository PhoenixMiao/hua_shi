package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.AddMemberRequest;
import com.phoenix.huashi.controller.request.AssignWorkRequest;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.service.MemberService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("项目成员相关操作")
@RestController
@RequestMapping("/member")
@Validated
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private SessionUtils sessionUtils;

    @Auth
    @PostMapping("/add")
    @ApiOperation(value = "添加项目成员", response = String.class)
    public Object addMember(@NotNull @Valid @RequestBody AddMemberRequest request) {
        return memberService.addMember(request);
    }

    @Auth
    @PostMapping("/work")
    @ApiOperation(value = "安排分工", response = String.class)
    public Object assignWork(@NotNull @Valid @RequestBody AssignWorkRequest assignWorkRequest) {
        String userChuangNum=sessionUtils.getUserChuangNum();
        memberService.assignWork(assignWorkRequest,userChuangNum);
        return "操作成功";
    }

}
