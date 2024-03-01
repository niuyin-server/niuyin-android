package com.roydon.niuyin.ui.fragment.me;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.PersonalProfileActivity;
import com.roydon.niuyin.ui.activity.SettingActivity;
import com.roydon.niuyin.ui.adapter.MeAdapter;
import com.roydon.niuyin.ui.dialog.SlideDialog;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

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