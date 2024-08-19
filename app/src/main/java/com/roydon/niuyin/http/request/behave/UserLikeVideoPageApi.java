package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 16:04
 * @description 分页用户点赞
 * 请求类型：post
 */
public class UserLikeVideoPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/like/userPage";
    }

    private Long userId;
    private String videoTitle;
    private int pageNum = 1;
    private int pageSize = 10;

    public UserLikeVideoPageApi setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserLikeVideoPageApi setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
        return this;
    }

    public UserLikeVideoPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public UserLikeVideoPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
