package com.roydon.niuyin.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.ViewPagerTabLayoutAdapter;
import com.roydon.niuyin.ui.fragment.followfans.FansFragment;
import com.roydon.niuyin.ui.fragment.followfans.FollowFragment;
import com.roydon.niuyin.ui.fragment.upload.UploadImageFragment;
import com.roydon.niuyin.ui.fragment.upload.UploadVideoFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class FollowFansActivity extends MyActivity {

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private Integer tabCur = 0;

    @DebugLog
    public static void start(Context context, Integer tabCur) {
        Intent intent = new Intent(context, FollowFansActivity.class);
        intent.putExtra(IntentKey.TAB_CUR, tabCur);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_fans;
    }

    @Override
    protected void initView() {
        tabCur = getInt(IntentKey.TAB_CUR);

        // tab
        initTabLayout(tabCur);
    }

    /**
     * 渲染tab
     */
    private void initTabLayout(Integer tabCur) {
        String[] mTitles = {"关注", "粉丝"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FollowFragment.newInstance());
        fragments.add(FansFragment.newInstance());
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new ViewPagerTabLayoutAdapter(getSupportFragmentManager(), mTitles, fragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(tabCur);
    }

    @Override
    protected void initData() {

    }

}