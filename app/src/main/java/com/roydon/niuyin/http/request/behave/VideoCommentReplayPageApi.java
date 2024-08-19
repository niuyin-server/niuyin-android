package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/4 20:43
 * @description 视频根评论分页
 * 请求类型：post
 */
public class VideoCommentReplayPageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/comment/replyPage";
    }

    private Long commentId;
    private String orderBy;
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public VideoCommentReplayPageApi setCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public VideoCommentReplayPageApi setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public VideoCommentReplayPageApi setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public VideoCommentReplayPageApi setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
