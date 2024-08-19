package com.roydon.niuyin.http.response.behave;

import java.time.LocalDateTime;

/**
 * 收藏夹vo
 */
public class FavoriteFolderVO extends UserFavorite {
    // 收藏视频数量
    private Long videoCount;
    // 该视频是否在此收藏夹中
    private Boolean weatherFavorite;

    public FavoriteFolderVO(Long videoCount, Boolean weatherFavorite) {
        this.videoCount = videoCount;
        this.weatherFavorite = weatherFavorite;
    }

    public FavoriteFolderVO(Long favoriteId, Long userId, String title, String description, String coverImage, LocalDateTime createTime, String showStatus, String delFlag, Long videoCount, Boolean weatherFavorite) {
        super(favoriteId, userId, title, description, coverImage, createTime, showStatus, delFlag);
        this.videoCount = videoCount;
        this.weatherFavorite = weatherFavorite;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }

    public Boolean getWeatherFavorite() {
        return weatherFavorite;
    }

    public void setWeatherFavorite(Boolean weatherFavorite) {
        this.weatherFavorite = weatherFavorite;
    }
}
