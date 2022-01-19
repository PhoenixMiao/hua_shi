package com.phoenix.huashi.service;

import com.phoenix.huashi.dto.SessionData;

public interface UserService {

    /**
     * 登录
     * @param code
     * @return
     */
    SessionData login(String code);
}
