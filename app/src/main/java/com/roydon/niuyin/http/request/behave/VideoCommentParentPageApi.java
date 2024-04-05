package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/4 20:43
 * @description 分页视频根评论
 * 请求类型：post
 */
public class VideoCommentParentPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/comment/parent";
    }

    private String videoId;
    private String orderBy;
    private int pageNum = 1;
    private int pageSize = 10;

    public VideoCommentParentPageApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public VideoCommentParentPageApi setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public VideoCommentParentPageApi setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public VideoCommentParentPageApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
