package com.roydon.niuyin.http.request.user;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/29 22:04
 * @description 获取用户详情
 * 请求类型：get
 */
public final class UserProfileApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/app/" + userId;
    }

    private Long userId;

    public UserProfileApi setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
