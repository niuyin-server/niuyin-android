package com.roydon.niuyin.ui.fragment;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.CopyActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
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