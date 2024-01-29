package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/29 19:49
 * @description niuyin-android
 */
public class LoginSmsApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/sms-login";
    }

    //手机号
    private String telephone;
    //验证码
    private String smsCode;

    public LoginSmsApi setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public LoginSmsApi setSmsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }
}