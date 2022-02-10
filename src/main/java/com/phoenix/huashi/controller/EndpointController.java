package com.phoenix.huashi.controller;

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
    @ApiOperation(value = "获取OSS域名(输入“OSS”时返回OSS域名)",response = String.class)
    public Object getOSS(@PathVariable("key")String key){
        if(key.equals("OSS")){
            return "https://huashi-1305159828.cos.ap-shanghai.myqcloud.com";
        }
        return "请输入“oss”";
    }
}
