package com.roydon.niuyin.http.request.social;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/21 19:59
 * @description 我的粉丝分页
 * post
 */
public class FansPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "social/api/v1/app/follow/fansPage";
    }

    private int pageNum = 1;
    private int pageSize = 10;

    public FansPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public FansPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}