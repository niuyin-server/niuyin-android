package com.roydon.niuyin.http.response.behave;

import java.time.LocalDateTime;

public class VideoCommentReplayVO extends VideoUserComment {
    // 评论者昵称
    private String nickName;
    // 评论者头像
    private String avatar;

    // 被回复者id
    private Long replayUserId;
    // 被回复者昵称
    private String replayUserNickName;

    public VideoCommentReplayVO(String nickName, String avatar, Long replayUserId, String replayUserNickName) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.replayUserId = replayUserId;
        this.replayUserNickName = replayUserNickName;
    }

    public VideoCommentReplayVO(Long commentId, String videoId, Long userId, Long parentId, Long originId, String content, Long likeNum, String status, LocalDateTime createTime, String nickName, String avatar, Long replayUserId, String replayUserNickName) {
        super(commentId, videoId, userId, parentId, originId, content, likeNum, status, createTime);
        this.nickName = nickName;
        this.avatar = avatar;
        this.replayUserId = replayUserId;
        this.replayUserNickName = replayUserNickName;
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

    public Long getReplayUserId() {
        return replayUserId;
    }

    public void setReplayUserId(Long replayUserId) {
        this.replayUserId = replayUserId;
    }

    public String getReplayUserNickName() {
        return replayUserNickName;
    }

    public void setReplayUserNickName(String replayUserNickName) {
        this.replayUserNickName = replayUserNickName;
    }
}
