package com.roydon.niuyin.http.response.video;

import java.time.LocalDateTime;

public class MyVideoVO {

    private String videoId;
    private String videoTitle; // 标题
    private String coverImage; // 封面
    private String publishType; // 发布类型（0视频，1图文）
    private LocalDateTime createTime; // 发布时间
    private Long likeNum;  // 点赞量

    public MyVideoVO(String videoId, String videoTitle, String coverImage, String publishType, LocalDateTime createTime, Long likeNum) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.publishType = publishType;
        this.createTime = createTime;
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "MyVideoVO{" +
                "videoId='" + videoId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", publishType='" + publishType + '\'' +
                ", createTime=" + createTime +
                ", likeNum=" + likeNum +
                '}';
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }
}
