package com.roydon.niuyin.http.request.behave;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/4/24 11:24
 * @description 收藏夹集合接口
 */
public class FavoriteFolderListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "behave/api/v1/app/userFavorite/list/" + videoId;
    }

    private String videoId;

    public FavoriteFolderListApi setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
}
