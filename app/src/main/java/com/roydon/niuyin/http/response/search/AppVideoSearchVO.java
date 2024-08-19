package com.roydon.niuyin.http.response.search;

import com.roydon.niuyin.http.response.Author;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * 视频搜索响应体
 */
public class AppVideoSearchVO {

    // 视频id
    private String videoId;
    // 标题
    private String videoTitle;
    // 视频封面
    private String coverImage;
    private String publishType;
    // 文章发布时间
    private Date publishTime;
    // 标签
    private String[] tags;

    private Long viewNum; //观看量

    private LocalDateTime createTime; //发布时间

    private Long userId;
    private Author author;

    public AppVideoSearchVO(String videoId, String videoTitle, String coverImage, String publishType, Date publishTime, String[] tags, Long viewNum, LocalDateTime createTime, Long userId, Author author) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.publishType = publishType;
        this.publishTime = publishTime;
        this.tags = tags;
        this.viewNum = viewNum;
        this.createTime = createTime;
        this.userId = userId;
        this.author = author;
    }

    @Override
    public String toString() {
        return "AppVideoSearchVO{" +
                "videoId='" + videoId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", publishType='" + publishType + '\'' +
                ", publishTime=" + publishTime +
                ", tags=" + Arrays.toString(tags) +
                ", viewNum=" + viewNum +
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

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
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
