package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    desc   : 退出登录
 */
public class LogoutApi implements IRequestApi {

    @Override
    public String getApi() {
        return "user/logout";
    }
}