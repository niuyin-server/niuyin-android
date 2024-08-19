package com.roydon.niuyin.http.response.social;

public class DynamicUser {
    private Long userId;
    private String nickname;
    private String avatar;
    // 是否查看过动态
    private Boolean hasRead;

    public DynamicUser() {
    }

    public DynamicUser(Long userId, String nickname, String avatar, Boolean hasRead) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.hasRead = hasRead;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }
}
