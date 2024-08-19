package com.roydon.niuyin.ui.fragment;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.CopyActivity;

/**
 *    desc   : 可进行拷贝的副本
 */
public final class CopyFragment extends MyFragment<CopyActivity> {

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_copy;
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