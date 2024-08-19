package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/7 19:30
 * @description 关注用户动态
 * get
 */
public class FollowDynamicApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/dynamic";
    }

}