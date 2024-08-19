package com.roydon.niuyin.http.response.search;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视频搜索记录表
 */
public class VideoSearchHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 搜索词
     */
    private String keyword;

    /**
     * 平台 0web;1app
     */
    private String platform;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    public VideoSearchHistory(String id, Long userId, String keyword, String platform, LocalDateTime createdTime) {
        this.id = id;
        this.userId = userId;
        this.keyword = keyword;
        this.platform = platform;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "VideoSearchHistory{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", keyword='" + keyword + '\'' +
                ", platform='" + platform + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
