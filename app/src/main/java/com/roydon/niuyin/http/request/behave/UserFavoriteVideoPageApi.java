package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 16:04
 * @description 分页用户收藏
 * 请求类型：post
 */
public class UserFavoriteVideoPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/favorite/userPage";
    }

    private Long userId;
    private String videoTitle;
    private int pageNum = 1;
    private int pageSize = 10;

    public UserFavoriteVideoPageApi setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserFavoriteVideoPageApi setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
        return this;
    }

    public UserFavoriteVideoPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public UserFavoriteVideoPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
