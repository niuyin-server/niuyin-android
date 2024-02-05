package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    desc   : 用户登录
 */
public class LoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "user/login";
    }

    /** 手机号 */
    private String phone;
    /** 登录密码 */
    private String password;

    public LoginApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LoginApi setPassword(String password) {
        this.password = password;
        return this;
    }
}