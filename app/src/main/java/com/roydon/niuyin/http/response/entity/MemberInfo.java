package com.roydon.niuyin.http.response.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberInfo implements Serializable {
    private static final long serialVersionUID = -18427092522208701L;
    /**
     * id
     */
    private Long infoId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 个人页面背景图片
     */
    private String backImage;
    /**
     * 个人描述
     */
    private String description;
    /**
     * 生日
     */
    private LocalDateTime birthday;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 邮编
     */
    private String adcode;
    /**
     * 学校
     */
    private String campus;
    /**
     * 喜欢视频向外展示状态：0展示1隐藏
     */
    private String likeShowStatus;

    /**
     * 收藏视频向外展示状态：0展示1隐藏
     */
    private String favoriteShowStatus;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getLikeShowStatus() {
        return likeShowStatus;
    }

    public void setLikeShowStatus(String likeShowStatus) {
        this.likeShowStatus = likeShowStatus;
    }

    public String getFavoriteShowStatus() {
        return favoriteShowStatus;
    }

    public void setFavoriteShowStatus(String favoriteShowStatus) {
        this.favoriteShowStatus = favoriteShowStatus;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
                "infoId=" + infoId +
                ", userId=" + userId +
                ", backImage='" + backImage + '\'' +
                ", description='" + description + '\'' +
                ", birthday=" + birthday +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", adcode='" + adcode + '\'' +
                ", campus='" + campus + '\'' +
                ", likeShowStatus='" + likeShowStatus + '\'' +
                ", favoriteShowStatus='" + favoriteShowStatus + '\'' +
                '}';
    }
}
