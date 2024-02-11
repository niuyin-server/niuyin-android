package com.roydon.niuyin.http.response.notice;

import com.roydon.niuyin.http.response.entity.Notice;

import java.time.LocalDateTime;

public class NoticeVO extends Notice {
    private String nickName;
    private String operateAvatar;
    private String videoCoverImage;

    public NoticeVO(Long noticeId, Long operateUserId, Long noticeUserId, String videoId, Long commentId, String content, String remark, String noticeType, String receiveFlag, LocalDateTime createTime, String nickName, String operateAvatar, String videoCoverImage) {
        super(noticeId, operateUserId, noticeUserId, videoId, commentId, content, remark, noticeType, receiveFlag, createTime);
        this.nickName = nickName;
        this.operateAvatar = operateAvatar;
        this.videoCoverImage = videoCoverImage;
    }

    @Override
    public String toString() {
        return "NoticeVO{" +
                "nickName='" + nickName + '\'' +
                ", operateAvatar='" + operateAvatar + '\'' +
                ", videoCoverImage='" + videoCoverImage + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOperateAvatar() {
        return operateAvatar;
    }

    public void setOperateAvatar(String operateAvatar) {
        this.operateAvatar = operateAvatar;
    }

    public String getVideoCoverImage() {
        return videoCoverImage;
    }

    public void setVideoCoverImage(String videoCoverImage) {
        this.videoCoverImage = videoCoverImage;
    }
}
