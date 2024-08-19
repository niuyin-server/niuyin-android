package com.roydon.niuyin.http.response.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notice implements Serializable {
    private static final long serialVersionUID = -60869533333103048L;
    /**
     * 通知id
     */
    private Long noticeId;
    /**
     * 用户id
     */
    private Long operateUserId;
    // 被通知的用户
    private Long noticeUserId;
    private String videoId;
    private Long commentId;
    /**
     * 内容
     */
    private String content;
    private String remark;
    /**
     * 通知类型(0：点赞，1：关注，2：收藏、3:视频被评论，4：回复评论、5：赞了评论)
     */
    private String noticeType;
    /**
     * 接收标志(0：未读 1：已读)
     */
    private String receiveFlag;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", operateUserId=" + operateUserId +
                ", noticeUserId=" + noticeUserId +
                ", videoId='" + videoId + '\'' +
                ", commentId=" + commentId +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", receiveFlag='" + receiveFlag + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Notice(Long noticeId, Long operateUserId, Long noticeUserId, String videoId, Long commentId, String content, String remark, String noticeType, String receiveFlag, LocalDateTime createTime) {
        this.noticeId = noticeId;
        this.operateUserId = operateUserId;
        this.noticeUserId = noticeUserId;
        this.videoId = videoId;
        this.commentId = commentId;
        this.content = content;
        this.remark = remark;
        this.noticeType = noticeType;
        this.receiveFlag = receiveFlag;
        this.createTime = createTime;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public Long getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(Long noticeUserId) {
        this.noticeUserId = noticeUserId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getReceiveFlag() {
        return receiveFlag;
    }

    public void setReceiveFlag(String receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
