package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/1 19:33
 * @description 热门视频分页
 * 请求类型：post
 */
public class HotVideoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/hotVideo";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public HotVideoApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public HotVideoApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
