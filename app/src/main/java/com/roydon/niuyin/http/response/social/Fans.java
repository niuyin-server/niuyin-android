package com.roydon.niuyin.http.response.social;

public class Fans {
    private Long userId;
    private String avatar;
    private String nickName;
    private String sex;
    private Boolean weatherFollow; // 是否关注

    public Fans() {
    }

    public Fans(Long userId, String avatar, String nickName, String sex, Boolean weatherFollow) {
        this.userId = userId;
        this.avatar = avatar;
        this.nickName = nickName;
        this.sex = sex;
        this.weatherFollow = weatherFollow;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getWeatherFollow() {
        return weatherFollow;
    }

    public void setWeatherFollow(Boolean weatherFollow) {
        this.weatherFollow = weatherFollow;
    }
}
