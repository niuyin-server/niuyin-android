package com.roydon.niuyin.ui.fragment.followfans;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.FollowFansActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;

public final class FansFragment extends MyFragment<FollowFansActivity> {

    public static FansFragment newInstance() {
        return new FansFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fans;
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