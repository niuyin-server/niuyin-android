package com.roydon.niuyin.http.request.user;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/29 22:04
 * @description 获取个人详情-根据token
 * 请求类型：get
 */
public final class UserInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "member/api/v1/userinfo";
    }
}
