package com.phoenix.huashi.controller;

import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.controller.request.AddMemberRequest;
import com.phoenix.huashi.controller.request.GetMessageListReuqest;
import com.phoenix.huashi.service.MemberService;
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

    @Auth
    @PostMapping("/add")
    @ApiOperation(value = "添加项目成员", response = String.class)
    public Object addMember(@NotNull @Valid @RequestBody AddMemberRequest request) {
        return memberService.addMember(request);
    }

}
