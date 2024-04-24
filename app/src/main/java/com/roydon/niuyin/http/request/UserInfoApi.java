package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    desc   : 获取用户信息
 */
public class UserInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "user/info";
    }
}