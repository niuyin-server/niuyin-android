package com.roydon.niuyin.ui.fragment;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.CopyActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;

/**
 * desc   : 上传
 */
public final class FragmentUpload extends MyFragment<HomeActivity> {

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    public static FragmentUpload newInstance() {
        return new FragmentUpload();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}