package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/29 19:58
 * @description 获取登录验证码
 */
public class GetLoginAuthCodeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/loginAuthCode/" + this.telephone;
    }

    // 手机号
    private String telephone;

    public GetLoginAuthCodeApi setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }
}