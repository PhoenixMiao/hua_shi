package com.phoenix.huashi.controller;

import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.util.SessionUtils;
import com.phoenix.huashi.dto.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phoenix
 * @version 2022/1/19 19:21
 */
@RestController
public class MyController {

    @Autowired
    private SessionUtils sessionUtils;

    @GetMapping("/get")
    @Deprecated
    public Result get() {
        try{
        sessionUtils.getUserChuangNum();
        SessionData sessionData = sessionUtils.getSessionData();
        return Result.success(null);
        }catch (CommonException e){
        return Result.result(e.getCommonErrorCode());
        }
    }

}
