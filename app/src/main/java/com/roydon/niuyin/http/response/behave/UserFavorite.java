package com.roydon.niuyin.http.response.behave;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (UserFavorite)实体类
 *
 * @author lzq
 * @since 2023-11-13 14:56:12
 */
public class UserFavorite implements Serializable {
    private static final long serialVersionUID = 449453316357090990L;
    /**
     * 收藏夹id
     */
    private Long favoriteId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收藏夹名称
     */
    private String title;
    /**
     * 收藏夹描述
     */
    private String description;
    /**
     * 收藏夹封面
     */
    private String coverImage;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 0:别人可见，1:陌生人不可见
     */
    private String showStatus;
    /**
     * 0存在，1删除
     */
    private String delFlag;

    public UserFavorite() {
    }

    public UserFavorite(Long favoriteId, Long userId, String title, String description, String coverImage, LocalDateTime createTime, String showStatus, String delFlag) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
        this.createTime = createTime;
        this.showStatus = showStatus;
        this.delFlag = delFlag;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}

