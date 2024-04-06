package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.square.SquareImageView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.request.user.UserProfileApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.MeAdapter;
import com.roydon.niuyin.ui.adapter.UserProfileViewPagerAdapter;
import com.roydon.niuyin.ui.fragment.me.MeFavoriteFragment;
import com.roydon.niuyin.ui.fragment.me.MeLikeFragment;
import com.roydon.niuyin.ui.fragment.me.MePostFragment;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class UserProfileActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_USERPROFILE = 1;
    private static final int HANDLER_USERPROFILE_ERROR = 2;

    @BindView(R.id.collapsingToolbarLayout)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.iv_menu_avatar)
    ImageView mMenuAvatarView;
    @BindView(R.id.tv_menu_nickname)
    TextView mMenuNicknameView;
    @BindView(R.id.iv_menu_search)
    SquareImageView mMenuSearchView;
    @BindView(R.id.iv_menu_list)
    SquareImageView mMenuListView;

    // 用户信息
    @BindView(R.id.ll_userinfo)
    LinearLayout mUserinfoView;
    @BindView(R.id.iv_user_bg)
    ImageView mUserBgView;
    @BindView(R.id.iv_avatar)
    ImageView mAvatarView;
    @BindView(R.id.tv_nickname)
    TextView mNickNameView;
    @BindView(R.id.tv_user_id)
    TextView mUserIdView;

    private MemberInfoVO memberInfoVO;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @DebugLog
    public static void start(Context context, Long userId) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(IntentKey.USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void initView() {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
    }

    @Override
    protected void initData() {
        getUserProfile();

    }

    /**
     * 渐变状态变化
     *
     * @param layout
     * @param shown  渐变开关
     */
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            getStatusBarConfig().statusBarDarkFont(true).init();
            mUserinfoView.setVisibility(View.GONE);
            mMenuAvatarView.setVisibility(View.VISIBLE);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuSearchView.setImageResource(R.drawable.ic_search_b);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuListView.setImageResource(R.drawable.ic_more_b_h);
            mMenuNicknameView.setVisibility(View.VISIBLE);
        } else {
            getStatusBarConfig().statusBarDarkFont(false).init();
            mUserinfoView.setVisibility(View.VISIBLE);
            mMenuAvatarView.setVisibility(View.GONE);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuSearchView.setImageResource(R.drawable.ic_search_w);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuListView.setImageResource(R.drawable.ic_more_w_h);
            mMenuNicknameView.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void showUserProfile(MemberInfoVO memberInfoVO) {
        if (memberInfoVO.getAvatar() != null && !memberInfoVO.getAvatar().equals("")) {
            GlideApp.with(this)
                    .load(memberInfoVO.getAvatar())
                    .circleCrop()
                    .into(mAvatarView);
            GlideApp.with(this)
                    .load(memberInfoVO.getAvatar())
                    .circleCrop()
                    .into(mMenuAvatarView);
            mMenuAvatarView.setVisibility(View.GONE);
        }
        if (memberInfoVO.getMemberInfo().getBackImage() != null && !memberInfoVO.getMemberInfo().getBackImage().equals("")) {
            GlideApp.with(this)
                    .load(memberInfoVO.getMemberInfo().getBackImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mUserBgView);
        }
        mNickNameView.setText(memberInfoVO.getNickName());
        mMenuNicknameView.setText(memberInfoVO.getNickName());
        mMenuNicknameView.setVisibility(View.GONE);
        mUserIdView.setText(memberInfoVO.getUserId().toString());
        // 设置 SlidingTabLayout
        // tab
        ArrayList<String> mTitles = new ArrayList<>();
        ArrayList<Fragment> mMeFragments = new ArrayList<>();
        mTitles.add("作品");
        mMeFragments.add(MePostFragment.newInstance());
        if (memberInfoVO.getMemberInfo().getLikeShowStatus().equals("0")) {
            mTitles.add("喜欢");
            mMeFragments.add(MeLikeFragment.newInstance());
        }
        if (memberInfoVO.getMemberInfo().getFavoriteShowStatus().equals("0")) {
            mTitles.add("收藏");
            mMeFragments.add(MeFavoriteFragment.newInstance());
        }
        mViewPager.setOffscreenPageLimit(mMeFragments.size());
        mViewPager.setAdapter(new UserProfileViewPagerAdapter(getSupportFragmentManager(), mTitles, mMeFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    break;
                case HANDLER_USERPROFILE:
                    showUserProfile(memberInfoVO);
                    break;
                default:
                    break;
            }
        }
    };

    private void getUserProfile() {
        EasyHttp.get(this)
                .api(new UserProfileApi()
                        .setUserId(getLong(IntentKey.USER_ID)))
                .request(new HttpCallback<HttpData<MemberInfoVO>>(this) {

                    @Override
                    public void onSucceed(HttpData<MemberInfoVO> data) {
                        memberInfoVO = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_USERPROFILE);
                    }
                });
    }
}