package com.roydon.niuyin.ui.fragment.me.favorite;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;

/**
 * @author roydon
 * @date 2024/1/31 12:00
 * @description 收藏夹
 */
public class MeFavoriteFolderFragment extends MyFragment<HomeActivity> {

    public static MeFavoriteFolderFragment newInstance() {
        return new MeFavoriteFolderFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_favorite_folder;
    }

    @Override
    protected boolean statusBarDarkFont() {
        return !super.statusBarDarkFont();
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