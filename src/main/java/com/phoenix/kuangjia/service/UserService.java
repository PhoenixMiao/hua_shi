package com.phoenix.kuangjia.service;

import com.phoenix.kuangjia.dto.SessionData;

public interface UserService {

    /**
     * 登录
     * @param code
     * @return
     */
    SessionData login(String code);
}
