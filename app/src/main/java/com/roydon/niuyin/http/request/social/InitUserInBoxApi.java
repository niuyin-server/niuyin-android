package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/7 19:30
 * @description 初始化用户收件箱
 * get
 */
public class InitUserInBoxApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/initUserInBox";
    }
}