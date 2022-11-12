package com.phoenix.huashi.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.service.VoteService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Api("投票相关操作")
@RestController
@RequestMapping("/vote")
@Validated
public class VoteController {
    @Autowired
    private VoteService voteService;

    @GetMapping("")
    @ApiOperation(value = "投票更新", response = String.class)
    public Result voteUpdate() {
        try {
            voteService.updateVote();
            return Result.success(null);
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }
}
