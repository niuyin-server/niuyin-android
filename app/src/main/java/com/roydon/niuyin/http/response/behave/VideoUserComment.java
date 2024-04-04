package com.roydon.niuyin.http.response.behave;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (VideoUserComment)实体类
 *
 * @author roydon
 * @since 2023-10-30 16:52:51
 */
public class VideoUserComment implements Serializable {
    private static final long serialVersionUID = -19005250815450923L;
    /**
     * 评论id
     */
    private Long commentId;
    /**
     * 视频id
     */
    private String videoId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 评论的根id
     */
    private Long originId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 点赞量
     */
    private Long likeNum;
    /**
     * 状态：0默认1禁止
     */
    private String status;

    private LocalDateTime createTime;

    public VideoUserComment() {
    }

    public VideoUserComment(Long commentId, String videoId, Long userId, Long parentId, Long originId, String content, Long likeNum, String status, LocalDateTime createTime) {
        this.commentId = commentId;
        this.videoId = videoId;
        this.userId = userId;
        this.parentId = parentId;
        this.originId = originId;
        this.content = content;
        this.likeNum = likeNum;
        this.status = status;
        this.createTime = createTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

