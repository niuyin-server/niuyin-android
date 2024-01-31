package com.roydon.niuyin.ui.fragment;

import android.view.View;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;

/**
 *    desc   : 项目框架使用示例
 */
public final class FragmentMessage extends MyFragment<HomeActivity> {

    public static FragmentMessage newInstance() {
        return new FragmentMessage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        setOnClickListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}