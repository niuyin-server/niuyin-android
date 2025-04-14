package com.roydon.niuyin.helper.event;

public class UserLoginEvent {
    private Long userId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    private String avatar;

    public UserLoginEvent() {
    }

    public UserLoginEvent(Long userId, String userName, String nickName, String avatar) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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