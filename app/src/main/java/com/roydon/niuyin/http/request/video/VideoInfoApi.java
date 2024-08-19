package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/1/31 23:09
 * @description 视频信息
 * 请求类型：get
 */
public class VideoInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/info/" + this.videoId;
    }

    /**
     * 视频id
     */
    private String videoId;

    public VideoInfoApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
