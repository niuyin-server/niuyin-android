package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 16:04
 * @description 分页用户作品
 * 请求类型：post
 */
public class UserPostPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/userPage";
    }

    private Long userId;
    private String videoTitle;
    private int pageNum = 1;
    private int pageSize = 10;

    public UserPostPageApi setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserPostPageApi setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
        return this;
    }

    public UserPostPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public UserPostPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
