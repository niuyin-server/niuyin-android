package com.roydon.niuyin.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoCategoryActivity;
import com.roydon.niuyin.ui.activity.VideoSearchActivity;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.ui.fragment.index.IndexFollowFragment;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc   : 项目炫酷效果示例
 */
public final class IndexFragment extends MyFragment<HomeActivity> implements XCollapsingToolbarLayout.OnScrimsListener {

    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.iv_category)
    ImageView mVideoCategoryView;

    @BindView(R.id.iv_avatar)
    ImageView mAvatarView;
    @BindView(R.id.tv_search_hint)
    TextView mHintView;
    @BindView(R.id.iv_test_search)
    ImageView mSearchView;

    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView() {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        String avatarCache = SPUtils.getString(SPUtils.AVATAR, "", getContext());
        if (avatarCache.equals("")) {
            GlideApp.with(this)
                    .load(R.drawable.ic_avatar_default)
                    .circleCrop()
                    .into(mAvatarView);
        } else {
            GlideApp.with(this)
                    .load(avatarCache)
                    .circleCrop()
                    .into(mAvatarView);
        }
        // tab
        String[] mTitles = {"关注", "推荐", "热门"};
        ArrayList<Fragment> mIndexFragments = new ArrayList<>();
        mIndexFragments.add(IndexFollowFragment.newInstance());
        mIndexFragments.add(IndexRecommendFragment.newInstance());
        mIndexFragments.add(IndexHotFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mIndexFragments.size());
        mViewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mIndexFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(1);
        // 点击事件监听
        mHintView.setOnClickListener(v -> {
            startActivity(VideoSearchActivity.class);
        });
        mVideoCategoryView.setOnClickListener(v -> {
            startActivity(VideoCategoryActivity.class);
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * CollapsingToolbarLayout 渐变回调
     * <p>
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            mHintView.setBackgroundResource(R.drawable.bg_home_search_bar_gray);
            mHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black60));
            mSearchView.setImageResource(R.drawable.ic_search_b);
            getStatusBarConfig().statusBarDarkFont(true).init();
        } else {
            mHintView.setBackgroundResource(R.drawable.bg_home_search_bar_transparent);
            mHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white60));
            mSearchView.setImageResource(R.drawable.ic_search_white);
            getStatusBarConfig().statusBarDarkFont(true).init();
        }
    }
}