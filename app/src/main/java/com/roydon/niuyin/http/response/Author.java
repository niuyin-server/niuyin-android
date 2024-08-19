package com.roydon.niuyin.http.response;

import java.io.Serializable;

public class Author implements Serializable {
    private static final long serialVersionUID = 5397131L;
    private Long userId;
    private String userName;
    private String nickName;
    private String avatar;

    @Override
    public String toString() {
        return "Author{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public Author(Long userId, String userName, String nickName, String avatar) {
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
