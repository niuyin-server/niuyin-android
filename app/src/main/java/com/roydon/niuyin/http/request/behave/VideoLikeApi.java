package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/23 17:37
 * @description 视频点赞
 * get
 */
public class VideoLikeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/like/" + videoId;
    }

    private String videoId;

    public VideoLikeApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
