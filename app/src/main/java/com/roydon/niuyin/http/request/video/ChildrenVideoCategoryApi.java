package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/5 14:36
 * @description 视频分类子一级列表
 * get
 */
public class ChildrenVideoCategoryApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/category/children/" + this.id;
    }

    private Long id;

    public ChildrenVideoCategoryApi setId(Long id) {
        this.id = id;
        return this;
    }
}
