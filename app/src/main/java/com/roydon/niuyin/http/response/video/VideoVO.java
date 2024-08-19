package com.roydon.niuyin.http.response.video;

import com.roydon.niuyin.http.response.entity.Video;
import com.roydon.niuyin.http.response.entity.VideoPosition;

import java.time.LocalDateTime;
import java.util.Arrays;

public class VideoVO extends Video {

    private Long commentNum;
    private String userNickName;
    private String userAvatar;
    // 是否点赞
    private boolean weatherLike;
    // 是否收藏
    private boolean weatherFavorite;
    // 是否关注
    private boolean weatherFollow;
    // 标签数组
    private String[] tags;
    // 图片集合
    private String[] imageList;
    // 位置信息
    private VideoPosition position;

    // 热力值
    private Double hotScore;

    public VideoVO(String videoId, Long userId, String videoTitle, String videoDesc, String coverImage, String videoUrl, Long viewNum, Long likeNum, Long favoritesNum, String publishType, String showType, String positionFlag, String auditsStatus, String videoInfo, String delFlag, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, Long commentNum, String userNickName, String userAvatar, boolean weatherLike, boolean weatherFavorite, boolean weatherFollow, String[] tags, String[] imageList, VideoPosition position, Double hotScore) {
        super(videoId, userId, videoTitle, videoDesc, coverImage, videoUrl, viewNum, likeNum, favoritesNum, publishType, showType, positionFlag, auditsStatus, videoInfo, delFlag, createBy, createTime, updateBy, updateTime);
        this.commentNum = commentNum;
        this.userNickName = userNickName;
        this.userAvatar = userAvatar;
        this.weatherLike = weatherLike;
        this.weatherFavorite = weatherFavorite;
        this.weatherFollow = weatherFollow;
        this.tags = tags;
        this.imageList = imageList;
        this.position = position;
        this.hotScore = hotScore;
    }

    @Override
    public String toString() {
        return "VideoVO{" +
                "commentNum=" + commentNum +
                ", userNickName='" + userNickName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", weatherLike=" + weatherLike +
                ", weatherFavorite=" + weatherFavorite +
                ", weatherFollow=" + weatherFollow +
                ", tags=" + Arrays.toString(tags) +
                ", imageList=" + Arrays.toString(imageList) +
                ", position=" + position +
                ", hotScore=" + hotScore +
                '}';
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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

    public boolean isWeatherFollow() {
        return weatherFollow;
    }

    public void setWeatherFollow(boolean weatherFollow) {
        this.weatherFollow = weatherFollow;
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
