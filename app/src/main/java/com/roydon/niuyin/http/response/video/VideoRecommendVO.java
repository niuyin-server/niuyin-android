package com.roydon.niuyin.http.response.video;

import com.roydon.niuyin.http.response.Author;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VideoRecommendVO implements Serializable {
    private static final long serialVersionUID = -657506685397131L;
    private String videoId;
    private String videoTitle; //标题
    private String coverImage; //封面
    private Long viewNum; //观看量
    private Long likeNum;  // 点赞量
    private Long favoriteNum;  // 收藏量
    private Long commentNum; //评论量
    private String publishType; //发布类型（0视频，1图文）
    private String videoInfo;
    private LocalDateTime createTime; //发布时间
    private Long userId;
    private Author author;

    public VideoRecommendVO(String videoId, String videoTitle, String coverImage, Long viewNum, Long likeNum, Long favoriteNum, Long commentNum, String publishType, String videoInfo, LocalDateTime createTime, Long userId, Author author) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.viewNum = viewNum;
        this.likeNum = likeNum;
        this.favoriteNum = favoriteNum;
        this.commentNum = commentNum;
        this.publishType = publishType;
        this.videoInfo = videoInfo;
        this.createTime = createTime;
        this.userId = userId;
        this.author = author;
    }

    @Override
    public String toString() {
        return "VideoRecommendVO{" +
                "videoId='" + videoId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", viewNum=" + viewNum +
                ", likeNum=" + likeNum +
                ", favoriteNum=" + favoriteNum +
                ", commentNum=" + commentNum +
                ", publishType='" + publishType + '\'' +
                ", videoInfo='" + videoInfo + '\'' +
                ", createTime=" + createTime +
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

    public Long getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(Long favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
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
