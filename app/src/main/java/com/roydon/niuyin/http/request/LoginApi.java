package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 用户登录
 */
public class LoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/login";
    }

    /**
     * 手机号
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

    public LoginApi setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginApi setPassword(String password) {
        this.password = password;
        return this;
    }
}