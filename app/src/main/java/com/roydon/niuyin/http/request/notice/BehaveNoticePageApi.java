package com.roydon.niuyin.http.request.notice;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/11 20:01
 * @description 行为消息分页
 * post
 */
public class BehaveNoticePageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "notice/api/v1/app/behavePage";
    }

    private String noticeType;
    private Integer pageNum = 1;
    private Integer pageSize = 20;

    public BehaveNoticePageApi setNoticeType(String noticeType) {
        this.noticeType = noticeType;
        return this;
    }

    public BehaveNoticePageApi setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public BehaveNoticePageApi setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
