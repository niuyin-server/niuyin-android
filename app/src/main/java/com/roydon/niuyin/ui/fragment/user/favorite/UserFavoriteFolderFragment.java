package com.roydon.niuyin.ui.fragment.user.favorite;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.UserProfileActivity;

/**
 * @author roydon
 * @date 2024/1/31 12:00
 * @description 收藏夹
 */
public class UserFavoriteFolderFragment extends MyFragment<UserProfileActivity> {

    public static UserFavoriteFolderFragment newInstance() {
        return new UserFavoriteFolderFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_favorite_folder;
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