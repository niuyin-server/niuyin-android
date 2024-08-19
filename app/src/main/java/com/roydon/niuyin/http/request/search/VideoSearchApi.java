package com.roydon.niuyin.http.request.search;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/3 23:01
 * @description 视频搜索
 * /search/api/v1/app/video
 * post
 */
public class VideoSearchApi implements IRequestApi {

    @Override
    public String getApi() {
        return "search/api/v1/app/video";
    }

    private String keyword;
    private int pageNum;
    private int pageSize;
    private String publishTimeLimit;

    public VideoSearchApi setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public VideoSearchApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public VideoSearchApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public VideoSearchApi setPublishTimeLimit(String publishTimeLimit) {
        this.publishTimeLimit = publishTimeLimit;
        return this;
    }
}
