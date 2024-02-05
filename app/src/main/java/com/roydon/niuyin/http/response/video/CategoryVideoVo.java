package com.roydon.niuyin.http.response.video;

import com.roydon.niuyin.http.response.Author;

import java.time.LocalDateTime;

public class CategoryVideoVo {
    private String videoId;
    private String videoTitle; // 标题
    private String coverImage; // 封面
    private String publishType; // 发布类型（0视频，1图文）
    private String videoInfo;
    private LocalDateTime createTime; // 发布时间
    private Long viewNum; //观看量
    private Long likeNum;  // 点赞量
    private Long userId;
    private Author author;

    public CategoryVideoVo(String videoId, String videoTitle, String coverImage, String publishType, String videoInfo, LocalDateTime createTime, Long viewNum, Long likeNum, Long userId, Author author) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.publishType = publishType;
        this.videoInfo = videoInfo;
        this.createTime = createTime;
        this.viewNum = viewNum;
        this.likeNum = likeNum;
        this.userId = userId;
        this.author = author;
    }

    @Override
    public String toString() {
        return "CategoryVideoVo{" +
                "videoId='" + videoId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", publishType='" + publishType + '\'' +
                ", videoInfo='" + videoInfo + '\'' +
                ", createTime=" + createTime +
                ", viewNum=" + viewNum +
                ", likeNum=" + likeNum +
                ", userId=" + userId +
                ", author=" + author +
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

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
