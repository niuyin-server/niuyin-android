package com.roydon.niuyin.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    desc   : 获取验证码
 */
public class GetCodeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "code/get";
    }

    /** 手机号 */
    private String phone;

    public GetCodeApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}