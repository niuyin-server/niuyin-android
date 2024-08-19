package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/7 19:30
 * @description 关注用户
 * get
 */
public class FollowUserApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/" + userId;
    }

    private Long userId;

    public FollowUserApi setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

}