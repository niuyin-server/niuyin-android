package com.roydon.niuyin.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.ViewPagerTabLayoutAdapter;
import com.roydon.niuyin.ui.fragment.index.IndexFollowFragment;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.ui.fragment.upload.UploadImageFragment;
import com.roydon.niuyin.ui.fragment.upload.UploadVideoFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * desc   : 上传
 */
public final class FragmentUpload extends MyFragment<HomeActivity> {

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

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
        // tab
        String[] mTitles = {"视频", "图文"};
        ArrayList<Fragment> mUploadFragments = new ArrayList<>();
        mUploadFragments.add(UploadVideoFragment.newInstance());
        mUploadFragments.add(UploadImageFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mUploadFragments.size());
        mViewPager.setAdapter(new ViewPagerTabLayoutAdapter(getChildFragmentManager(), mTitles, mUploadFragments));
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