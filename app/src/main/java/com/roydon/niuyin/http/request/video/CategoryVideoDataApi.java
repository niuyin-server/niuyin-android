package com.roydon.niuyin.http.request.video;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/5 18:48
 * @description 分类视频分页
 * post
 */
public class CategoryVideoDataApi implements IRequestApi {

    @Override
    public String getApi() {
        return "video/api/v1/app/category/videoPage";
    }

    private Long id;
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public CategoryVideoDataApi setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryVideoDataApi setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public CategoryVideoDataApi setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
