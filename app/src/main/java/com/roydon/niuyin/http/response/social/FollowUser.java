package com.roydon.niuyin.http.response.social;


/**
 * FollowUser
 *
 * @AUTHOR: roydon
 * @DATE: 2024/4/21
 * 关注用户分页vo
 **/
public class FollowUser {
    private Long userId;
    private String avatar;
    private String nickName;
    private String sex;

    public FollowUser() {
    }

    public FollowUser(Long userId, String avatar, String nickName, String sex) {
        this.userId = userId;
        this.avatar = avatar;
        this.nickName = nickName;
        this.sex = sex;
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
}
