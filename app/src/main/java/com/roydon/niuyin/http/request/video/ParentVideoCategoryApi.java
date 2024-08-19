package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/5 14:36
 * @description 父视频分类列表
 * get
 */
public class ParentVideoCategoryApi implements IRequestApi {
    @Override
    public String getApi() {
        return "video/api/v1/app/category/parent";
    }
}
