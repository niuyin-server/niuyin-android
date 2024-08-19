package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/23 17:37
 * @description 视频取消点赞
 * get
 */
public class VideoUnlikeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/like/unlike/" + videoId;
    }

    private String videoId;

    public VideoUnlikeApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
