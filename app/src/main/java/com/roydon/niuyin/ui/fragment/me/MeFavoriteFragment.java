package com.roydon.niuyin.ui.fragment.me;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.MeAdapter;
import com.roydon.niuyin.ui.fragment.me.favorite.MeFavoriteFolderFragment;
import com.roydon.niuyin.ui.fragment.me.favorite.MeFavoriteVideoFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 12:00
 * @description 我的收藏fragment，包含两个子fragment
 *
 * @see MeFavoriteFolderFragment 收藏夹
 * @see MeFavoriteVideoFragment 收藏视频
 */
public class MeFavoriteFragment extends MyFragment<HomeActivity> {

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static MeFavoriteFragment newInstance() {
        return new MeFavoriteFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_favorite;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void lazyLoadData() {
        // tab
        String[] mTitles = {"收藏夹", "视频"};
        ArrayList<Fragment> mMeFragments = new ArrayList<>();
        mMeFragments.add(MeFavoriteFolderFragment.newInstance());
        mMeFragments.add(MeFavoriteVideoFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mMeFragments.size());
        mViewPager.setAdapter(new MeAdapter(getChildFragmentManager(), mTitles, mMeFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(1);
    }

    @Override
    protected void initData() {

    }
}