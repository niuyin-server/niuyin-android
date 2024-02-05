package com.roydon.niuyin.ui.fragment;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.CategoryVideoActivity;

/**
 * desc   : 分类视频数据
 */
public final class FragmentCategoryVideoData extends MyFragment<CategoryVideoActivity> {

    private Long id;//分类id

    public static FragmentCategoryVideoData newInstance(Long id) {
        FragmentCategoryVideoData fg = new FragmentCategoryVideoData();
        fg.id = id;
        return fg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_video_data;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {

    }
}