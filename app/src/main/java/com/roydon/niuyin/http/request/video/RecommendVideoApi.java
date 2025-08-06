package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/31 14:13
 * @description niuyin-android
 * 首页推荐视频
 * 请求类型：get
 */
public final class RecommendVideoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/app-api/api/v1/app/recommend";
    }
}
