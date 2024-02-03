package com.roydon.niuyin.http.request.search;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/3 22:35
 * @description 牛音热搜
 * post
 */
public class HotVideoSearchApi implements IRequestApi {

    @Override
    public String getApi() {
        return "search/api/v1/app/video/hotSearch";
    }

    private int pageNum;
    private int pageSize;

    public HotVideoSearchApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public HotVideoSearchApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
