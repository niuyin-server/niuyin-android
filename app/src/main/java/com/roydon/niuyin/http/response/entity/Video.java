package com.roydon.niuyin.http.response.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Video implements Serializable {
    private static final long serialVersionUID = 665174769504081543L;
    /**
     * 视频ID
     */
    private String videoId;
    /**
     * 用户id
     */
    private Long userId;

    private String videoTitle;

    private String videoDesc;
    /**
     * 视频封面地址
     */
    private String coverImage;
    /**
     * 视频地址
     */
    private String videoUrl;
    private Long viewNum;
    private Long likeNum;
    private Long favoritesNum;
    /**
     * 发布类型（0视频，1图文）
     */
    private String publishType;
    /**
     * 展示类型（0全部可见1好友可见2自己可见）
     */
    private String showType;
    /**
     * 定位功能0关闭1开启
     */
    private String positionFlag;
    /**
     * 审核状态(0:待审核1:审核成功2:审核失败)
     */
    private String auditsStatus;
    private String videoInfo;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public Video(String videoId, Long userId, String videoTitle, String videoDesc, String coverImage, String videoUrl, Long viewNum, Long likeNum, Long favoritesNum, String publishType, String showType, String positionFlag, String auditsStatus, String videoInfo, String delFlag, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime) {
        this.videoId = videoId;
        this.userId = userId;
        this.videoTitle = videoTitle;
        this.videoDesc = videoDesc;
        this.coverImage = coverImage;
        this.videoUrl = videoUrl;
        this.viewNum = viewNum;
        this.likeNum = likeNum;
        this.favoritesNum = favoritesNum;
        this.publishType = publishType;
        this.showType = showType;
        this.positionFlag = positionFlag;
        this.auditsStatus = auditsStatus;
        this.videoInfo = videoInfo;
        this.delFlag = delFlag;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId='" + videoId + '\'' +
                ", userId=" + userId +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoDesc='" + videoDesc + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", viewNum=" + viewNum +
                ", likeNum=" + likeNum +
                ", favoritesNum=" + favoritesNum +
                ", publishType='" + publishType + '\'' +
                ", showType='" + showType + '\'' +
                ", positionFlag='" + positionFlag + '\'' +
                ", auditsStatus='" + auditsStatus + '\'' +
                ", videoInfo='" + videoInfo + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
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

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
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

    public Long getFavoritesNum() {
        return favoritesNum;
    }

    public void setFavoritesNum(Long favoritesNum) {
        this.favoritesNum = favoritesNum;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getPositionFlag() {
        return positionFlag;
    }

    public void setPositionFlag(String positionFlag) {
        this.positionFlag = positionFlag;
    }

    public String getAuditsStatus() {
        return auditsStatus;
    }

    public void setAuditsStatus(String auditsStatus) {
        this.auditsStatus = auditsStatus;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

