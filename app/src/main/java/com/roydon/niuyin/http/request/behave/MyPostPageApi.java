package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 16:04
 * @description 分页我的作品
 * 请求类型：post
 */
public class MyPostPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/myPage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public MyPostPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public MyPostPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
