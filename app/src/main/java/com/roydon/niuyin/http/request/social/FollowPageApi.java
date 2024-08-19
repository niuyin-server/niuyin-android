package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/21 19:59
 * @description 我的关注分页
 * post
 */
public class FollowPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/followPage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public FollowPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public FollowPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}