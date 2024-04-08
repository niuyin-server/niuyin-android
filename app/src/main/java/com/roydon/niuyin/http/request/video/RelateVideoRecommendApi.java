package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/1 19:33
 * @description 相关视频推荐
 * 请求类型：get
 */
public class RelateVideoRecommendApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/relate/"+videoId;
    }

    private String videoId;

    public RelateVideoRecommendApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
