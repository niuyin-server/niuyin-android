package com.roydon.niuyin.ui.fragment.me;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.MeAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * desc   : 我的
 */
@SuppressLint("NonConstantResourceId")
public final class FragmentMeTab extends MyFragment<HomeActivity> {

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static FragmentMeTab newInstance() {
        return new FragmentMeTab();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_tab;
    }

    @Override
    protected boolean statusBarDarkFont() {
        return !super.statusBarDarkFont();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        // tab
        String[] mTitles = {"作品", "喜欢", "收藏"};
        ArrayList<Fragment> mMeFragments = new ArrayList<>();
        mMeFragments.add(MePostFragment.newInstance());
        mMeFragments.add(MeLikeFragment.newInstance());
        mMeFragments.add(MeFavoriteFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mMeFragments.size());
        mViewPager.setAdapter(new MeAdapter(getChildFragmentManager(), mTitles, mMeFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);

    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {

    }
}