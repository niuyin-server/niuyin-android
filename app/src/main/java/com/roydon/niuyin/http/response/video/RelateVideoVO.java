package com.roydon.niuyin.http.response.video;

import java.time.LocalDateTime;

/**
 * 相关视频推荐返回体
 *
 * @AUTHOR: roydon
 * @DATE: 2024/1/14
 **/
public class RelateVideoVO {

    private String videoId;
    private Long userId;
    private String videoTitle;
    /**
     * 视频封面地址
     */
    private String coverImage;

    /**
     * 发布类型（0视频，1图文）
     */
    private String publishType;
    /**
     * 视频详情
     */
    private String videoInfo;

    private LocalDateTime createTime;
    /**
     * 用户
     */
    private VideoAuthor videoAuthor;
    private VideoBehave videoBehave;
    private VideoSocial videoSocial;

    public static class VideoAuthor {
        private Long userId;
        private String nickName;
        private String avatar;

        public VideoAuthor(Long userId, String nickName, String avatar) {
            this.userId = userId;
            this.nickName = nickName;
            this.avatar = avatar;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
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
    }

    public static class VideoBehave {
        private Long viewNum;
        private Long likeNum;
        private Long favoriteNum;
        private Long commentNum;

        public VideoBehave(Long viewNum, Long likeNum, Long favoriteNum, Long commentNum) {
            this.viewNum = viewNum;
            this.likeNum = likeNum;
            this.favoriteNum = favoriteNum;
            this.commentNum = commentNum;
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
    }

    public static class VideoSocial {

        private Boolean weatherFollow;

        public VideoSocial(Boolean weatherFollow) {
            this.weatherFollow = weatherFollow;
        }

        public Boolean getWeatherFollow() {
            return weatherFollow;
        }

        public void setWeatherFollow(Boolean weatherFollow) {
            this.weatherFollow = weatherFollow;
        }
    }

    public RelateVideoVO(String videoId, Long userId, String videoTitle, String coverImage, String publishType, String videoInfo, LocalDateTime createTime, VideoAuthor videoAuthor, VideoBehave videoBehave, VideoSocial videoSocial) {
        this.videoId = videoId;
        this.userId = userId;
        this.videoTitle = videoTitle;
        this.coverImage = coverImage;
        this.publishType = publishType;
        this.videoInfo = videoInfo;
        this.createTime = createTime;
        this.videoAuthor = videoAuthor;
        this.videoBehave = videoBehave;
        this.videoSocial = videoSocial;
    }

    public RelateVideoVO() {
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

    public VideoAuthor getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(VideoAuthor videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public VideoBehave getVideoBehave() {
        return videoBehave;
    }

    public void setVideoBehave(VideoBehave videoBehave) {
        this.videoBehave = videoBehave;
    }

    public VideoSocial getVideoSocial() {
        return videoSocial;
    }

    public void setVideoSocial(VideoSocial videoSocial) {
        this.videoSocial = videoSocial;
    }
}
