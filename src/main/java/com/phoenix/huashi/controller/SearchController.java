package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.service.DisplayProjectService;
import com.phoenix.huashi.service.NotificationService;
import com.phoenix.huashi.service.RecruitProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api("搜索相关操作")
@RestController
@RequestMapping("/search")
@Validated
public class SearchController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RecruitProjectService recruitProjectService;

    @Autowired
    private DisplayProjectService displayProjectService;

    @PostMapping("/condition")
    @ApiOperation(value = "根据条件筛选信息")
    public Result search(@NotNull @Valid @RequestBody SearchRequest searchRequest) {
        try {
            if (searchRequest.getType() == 1)
                return Result.success(notificationService.searchNotification(searchRequest));
            if (searchRequest.getType() == 2)
                return Result.success(displayProjectService.searchDisplayProject(searchRequest));
            if (searchRequest.getType() == 3)
                return Result.success(recruitProjectService.searchRecruitProject(searchRequest));
            return Result.fail("tag值必须为1或2或3");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }

    }
}
