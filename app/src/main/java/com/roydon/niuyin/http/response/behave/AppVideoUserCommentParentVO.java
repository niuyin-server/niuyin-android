package com.roydon.niuyin.http.response.behave;

import java.time.LocalDateTime;

/**
 * app端视频父评论
 **/
public class AppVideoUserCommentParentVO extends VideoUserComment {

    // 评论者昵称
    private String nickName;
    // 评论者头像
    private String avatar;
    // 子评论数量
    private Long childrenCount;

    public AppVideoUserCommentParentVO(String nickName, String avatar, Long childrenCount) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.childrenCount = childrenCount;
    }

    public AppVideoUserCommentParentVO(Long commentId, String videoId, Long userId, Long parentId, Long originId, String content, String status, LocalDateTime createTime, String nickName, String avatar, Long childrenCount) {
        super(commentId, videoId, userId, parentId, originId, content, status, createTime);
        this.nickName = nickName;
        this.avatar = avatar;
        this.childrenCount = childrenCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }
}
