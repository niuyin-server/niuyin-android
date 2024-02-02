package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/2 20:45
 * @description 分页我的收藏
 * 请求类型：post
 */
public class MyFavoriteVideoPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/favorite/myPage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public MyFavoriteVideoPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public MyFavoriteVideoPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
