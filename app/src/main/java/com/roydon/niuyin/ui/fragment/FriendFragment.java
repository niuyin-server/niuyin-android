package com.roydon.niuyin.ui.fragment;

import android.view.View;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.SwitchButton;

import butterknife.BindView;

/**
 * desc   : 项目自定义控件展示
 */
public final class FriendFragment extends MyFragment<HomeActivity> {

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void initView() {
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