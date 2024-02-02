package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 16:04
 * @description 分页我的点赞
 * 请求类型：post
 */
public class MyLikeVideoPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/like/myLikePage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public MyLikeVideoPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public MyLikeVideoPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
