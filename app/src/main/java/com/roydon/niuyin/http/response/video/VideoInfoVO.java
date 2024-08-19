package com.roydon.niuyin.http.response.video;

import com.roydon.niuyin.http.response.Author;
import com.roydon.niuyin.http.response.entity.VideoPosition;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * VideoInfoVO
 *
 * @AUTHOR: roydon
 * @DATE: 2024/1/31
 **/
public class VideoInfoVO {
    private String videoId;
    private String videoTitle; // 标题
    private String coverImage; // 封面
    private String videoDesc; // 描述
    private String videoUrl; // 视频地址
    private Long viewNum; // 观看量
    private Long likeNum;  // 点赞量
    private Long favoriteNum;  // 收藏量
    private Long commentNum; // 评论量
    private String publishType; // 发布类型（0视频，1图文）
    private String positionFlag; // 定位功能（0关闭，1开启）
    private String videoInfo; //视频详情
    private LocalDateTime createTime; // 发布时间
    private Long userId;
    private Author author;
    // 是否关注
    private boolean weatherFollow;
    // 是否点赞
    private boolean weatherLike;
    // 是否收藏
    private boolean weatherFavorite;
    // 标签数组
    private String[] tags;
    // 图片集合
    private String[] imageList;
    // 位置信息
    private VideoPosition position;
    // 热力值
    private Double hotScore;

    public VideoInfoVO(String videoId, String videoTitle, String coverImage, String videoDesc, String videoUrl, Long viewNum, Long likeNum, Long favoriteNum, Long commentNum, String publishType, String positionFlag, String videoInfo, LocalDateTime createTime, Long userId, Author author, boolean weatherFollow, boolean weatherLike, boolean weatherFavorite, String[] tags, String[] imageList, VideoPosition position, Double hotScore) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.videoDesc = videoDesc;
        this.videoUrl = videoUrl;
        this.viewNum = viewNum;
        this.likeNum = likeNum;
        this.favoriteNum = favoriteNum;
        this.commentNum = commentNum;
        this.publishType = publishType;
        this.positionFlag = positionFlag;
        this.videoInfo = videoInfo;
        this.createTime = createTime;
        this.userId = userId;
        this.author = author;
        this.weatherFollow = weatherFollow;
        this.weatherLike = weatherLike;
        this.weatherFavorite = weatherFavorite;
        this.tags = tags;
        this.imageList = imageList;
        this.position = position;
        this.hotScore = hotScore;
    }

    @Override
    public String toString() {
        return "VideoInfoVO{" +
                "videoId='" + videoId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", videoDesc='" + videoDesc + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", viewNum=" + viewNum +
                ", likeNum=" + likeNum +
                ", favoriteNum=" + favoriteNum +
                ", commentNum=" + commentNum +
                ", publishType='" + publishType + '\'' +
                ", positionFlag='" + positionFlag + '\'' +
                ", videoInfo='" + videoInfo + '\'' +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", author=" + author +
                ", weatherFollow=" + weatherFollow +
                ", weatherLike=" + weatherLike +
                ", weatherFavorite=" + weatherFavorite +
                ", tags=" + Arrays.toString(tags) +
                ", imageList=" + Arrays.toString(imageList) +
                ", position=" + position +
                ", hotScore=" + hotScore +
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

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public String getPositionFlag() {
        return positionFlag;
    }

    public void setPositionFlag(String positionFlag) {
        this.positionFlag = positionFlag;
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

    public boolean isWeatherFollow() {
        return weatherFollow;
    }

    public void setWeatherFollow(boolean weatherFollow) {
        this.weatherFollow = weatherFollow;
    }

    public boolean isWeatherLike() {
        return weatherLike;
    }

    public void setWeatherLike(boolean weatherLike) {
        this.weatherLike = weatherLike;
    }

    public boolean isWeatherFavorite() {
        return weatherFavorite;
    }

    public void setWeatherFavorite(boolean weatherFavorite) {
        this.weatherFavorite = weatherFavorite;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }

    public VideoPosition getPosition() {
        return position;
    }

    public void setPosition(VideoPosition position) {
        this.position = position;
    }

    public Double getHotScore() {
        return hotScore;
    }

    public void setHotScore(Double hotScore) {
        this.hotScore = hotScore;
    }
}
