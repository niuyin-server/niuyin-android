package com.roydon.niuyin.ui.fragment;

import static com.roydon.niuyin.helper.SPManager.AVATAR;
import static com.roydon.niuyin.helper.SPManager.BACK_IMAGE;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.RxBus;
import com.roydon.niuyin.helper.SPManager;
import com.roydon.niuyin.helper.TokenManager;
import com.roydon.niuyin.helper.event.UserLoginEvent;
import com.roydon.niuyin.helper.player.PauseVideoEvent;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoCategoryActivity;
import com.roydon.niuyin.ui.activity.VideoSearchActivity;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.ui.fragment.index.IndexVideoFeedFragment;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * desc   : 首页
 */
public final class FragmentIndex extends MyFragment<HomeActivity> implements XCollapsingToolbarLayout.OnScrimsListener {

    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.iv_category)
    ImageView mVideoCategoryView;

    @BindView(R.id.iv_avatar)
    ImageView mAvatarView;
    @BindView(R.id.tv_search_hint)
    TextView mSearchHintView;
    @BindView(R.id.iv_test_search)
    ImageView mSearchView;
    private Subscription subscribe;
    ArrayList<Fragment> mIndexFragments;

    public static FragmentIndex newInstance() {
        return new FragmentIndex();
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

        // tab
        String[] mTitles = {"关注", "推荐", "热门"};
        mIndexFragments = new ArrayList<>();
//        mIndexFragments.add(IndexFollowFragment.newInstance());
        mIndexFragments.add(IndexVideoFeedFragment.newInstance());
        mIndexFragments.add(IndexRecommendFragment.newInstance());
        mIndexFragments.add(IndexHotFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mIndexFragments.size());
        mViewPager.setAdapter(new HomeAdapter(getChildFragmentManager(), mTitles, mIndexFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(1);
        // 点击事件监听
        setOnClickListener(R.id.tv_search_hint, R.id.iv_category);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RxBus.getDefault().post(new PauseVideoEvent(false));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void observeEvent() {
        // 监听播放或暂停事件
        subscribe = RxBus.getDefault().toObservable(UserLoginEvent.class).subscribe(new Action1<UserLoginEvent>() {
            @Override
            public void call(UserLoginEvent event) {
                GlideApp.with(getContext())
                        .load(event.getAvatar())
                        .circleCrop()
                        .into(mAvatarView);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_hint:
                startActivity(VideoSearchActivity.class);
                break;
            case R.id.iv_category:
                startActivity(VideoCategoryActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {
        if (TokenManager.getInstance(getActivity()).hasToken()) {
            apiGetUserInfo();
        }
//        observeEvent();
    }

    private void apiGetUserInfo() {
        EasyHttp.get(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<MemberInfoVO>>(getAttachActivity()) {

                    @Override
                    public void onSucceed(HttpData<MemberInfoVO> data) {
                        MemberInfoVO memberInfoVO = data.getData();
                        // 更新缓存
                        spSetString(AVATAR, memberInfoVO.getAvatar());
                        spSetString(BACK_IMAGE, memberInfoVO.getMemberInfo().getBackImage());
                        GlideApp.with(getAttachActivity())
                                .load(spGetString(SPManager.AVATAR))
                                .circleCrop()
                                .into(mAvatarView);
                        // 发送登录事件
//                        RxBus.getDefault().post(new UserLoginEvent(memberInfoVO.getUserId(), memberInfoVO.getUserName(), memberInfoVO.getNickName(), memberInfoVO.getAvatar()));
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onTabReClickRefresh() {
        mSlidingTabLayout.setCurrentTab(1);
        Fragment fragment = mIndexFragments.get(1);
        ((IndexRecommendFragment) fragment).onTabReClickRefresh();
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
            mSearchHintView.setBackgroundResource(R.drawable.bg_home_search_bar_gray);
            mSearchHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black60));
            mSearchView.setImageResource(R.drawable.ic_search_b);
            getStatusBarConfig().statusBarDarkFont(true).init();
        } else {
            mSearchHintView.setBackgroundResource(R.drawable.bg_home_search_bar_transparent);
            mSearchHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white60));
            mSearchView.setImageResource(R.drawable.ic_search_white);
            getStatusBarConfig().statusBarDarkFont(true).init();
        }
    }
}