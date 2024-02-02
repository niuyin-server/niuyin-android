package com.roydon.niuyin.ui.fragment;

import android.view.View;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;

/**
 * desc   : 朋友
 * author  : roydon
 */
public final class FragmentFriend extends MyFragment<HomeActivity> {

    public static FragmentFriend newInstance() {
        return new FragmentFriend();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend;
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

    @SingleClick
    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}