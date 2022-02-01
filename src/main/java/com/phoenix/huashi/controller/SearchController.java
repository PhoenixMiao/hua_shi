package com.phoenix.huashi.controller;

import com.phoenix.huashi.controller.request.SearchRequest;
import com.phoenix.huashi.controller.response.SearchResponse;
import com.phoenix.huashi.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
    private SearchService searchService;

    @PostMapping("/condition")
    @ApiOperation(value = "根据条件筛选信息",response = SearchResponse.class)
    public Object search(@NotNull @Valid @RequestBody SearchRequest searchRequest){
        return searchService.search(searchRequest);
    }
}
