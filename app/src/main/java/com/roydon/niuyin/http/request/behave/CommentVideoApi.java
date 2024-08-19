package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/4 20:43
 * @description 评论视频
 * 请求类型：post
 */
public class CommentVideoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/comment";
    }

    private String videoId;
    private String content;

    public CommentVideoApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public CommentVideoApi setContent(String content) {
        this.content = content;
        return this;
    }

}
