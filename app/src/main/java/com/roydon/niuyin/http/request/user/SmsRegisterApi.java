package com.roydon.niuyin.http.request.user;

import com.hjq.http.config.IRequestApi;

/**
 * desc   : 用户注册-sms
 */
public class SmsRegisterApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/app/sms-register";
    }

    /**
     * 手机号
     */
    private String telephone;
    /**
     * 验证码
     */
    private String smsCode;
    /**
     * 密码
     */
    private String password;
    private String confirmPassword;

    public SmsRegisterApi setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public SmsRegisterApi setSmsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }

    public SmsRegisterApi setPassword(String password) {
        this.password = password;
        return this;
    }

    public SmsRegisterApi setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}