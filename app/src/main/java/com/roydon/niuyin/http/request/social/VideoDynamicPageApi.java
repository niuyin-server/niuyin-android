package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/7 19:30
 * @description 关注用户视频动态
 * get
 */
public class VideoDynamicPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/dynamicVideoPage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public VideoDynamicPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public VideoDynamicPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}