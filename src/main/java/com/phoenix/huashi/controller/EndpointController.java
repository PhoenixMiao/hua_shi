package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("OSS域名")
@RestController
@RequestMapping("/endpoint")
@Validated
public class EndpointController {

    @GetMapping("/{key}")
    @ApiOperation(value = "获取OSS域名(输入“OSS”时返回OSS域名)", response = String.class)
    public Result getOSS(@PathVariable("key") String key) {
        try{
        if (key.equals("OSS")) {
            return Result.success("https://huashi-1305159828.cos.ap-shanghai.myqcloud.com");
        }
        return Result.fail("请输入“oss”");
    }catch (CommonException e){
        return Result.result(e.getCommonErrorCode());
        }
    }

}
