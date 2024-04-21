package com.roydon.niuyin.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
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
import com.hjq.widget.square.SquareImageView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.ui.activity.FollowFansActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.PersonalProfileActivity;
import com.roydon.niuyin.ui.activity.SettingActivity;
import com.roydon.niuyin.ui.adapter.MeAdapter;
import com.roydon.niuyin.ui.dialog.SlideDialog;
import com.roydon.niuyin.ui.fragment.me.MeFavoriteFragment;
import com.roydon.niuyin.ui.fragment.me.MeLikeFragment;
import com.roydon.niuyin.ui.fragment.me.MePostFragment;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * desc   : 我的
 */
@SuppressLint("NonConstantResourceId")
public final class FragmentMe extends MyFragment<HomeActivity> implements XCollapsingToolbarLayout.OnScrimsListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_GET_USERINFO = 1;

    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;

    @BindView(R.id.iv_menu_avatar)
    ImageView mMenuAvatarView;
    @BindView(R.id.tv_menu_nickname)
    TextView mMenuNicknameView;
    @BindView(R.id.iv_menu_search)
    SquareImageView mMenuSearchView;
    @BindView(R.id.iv_menu_list)
    SquareImageView mMenuListView;
    @BindView(R.id.ll_userinfo)
    LinearLayout mUserinfoView;

    // 用户信息
    @BindView(R.id.iv_user_bg)
    ImageView mUserBgView;
    @BindView(R.id.riv_avatar)
    ImageView mAvatarView;
    @BindView(R.id.tv_nickname)
    TextView mNickNameView;
    @BindView(R.id.tv_user_id)
    TextView mUserIdView;
    @BindView(R.id.ll_follows)
    LinearLayout followsLayout;
    @BindView(R.id.ll_fans)
    LinearLayout fansLayout;

    // 按钮
    @BindView(R.id.btn_edit_profile)
    Button mEditProfileBtn;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private MemberInfoVO memberInfoVO;

    public static FragmentMe newInstance() {
        return new FragmentMe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
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

        setOnClickListener(R.id.iv_menu_list, R.id.ll_userinfo, R.id.ll_follows, R.id.ll_fans);

    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {
        EasyHttp.get(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<MemberInfoVO>>(this.getAttachActivity()) {

                    @Override
                    public void onSucceed(HttpData<MemberInfoVO> data) {
                        memberInfoVO = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_GET_USERINFO);
                        // 更新缓存
                        spSetString(SPUtils.AVATAR, memberInfoVO.getAvatar());
                    }
                });

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
                case HANDLER_GET_USERINFO:
                    showUserInfo(memberInfoVO);
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("SetTextI18n")
    private void showUserInfo(MemberInfoVO memberInfoVO) {
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
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            getStatusBarConfig().statusBarDarkFont(true).init();
            mUserinfoView.setVisibility(View.GONE);
            mMenuAvatarView.setVisibility(View.VISIBLE);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuSearchView.setImageResource(R.drawable.ic_search_b);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuListView.setImageResource(R.drawable.ic_list_b);
            mMenuNicknameView.setVisibility(View.VISIBLE);
        } else {
            getStatusBarConfig().statusBarDarkFont(false).init();
            mUserinfoView.setVisibility(View.VISIBLE);
            mMenuAvatarView.setVisibility(View.GONE);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuSearchView.setImageResource(R.drawable.ic_search_w);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuListView.setImageResource(R.drawable.ic_list_w);
            mMenuNicknameView.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_list:
                // slide弹窗
                new SlideDialog.Builder(getAttachActivity())
                        .setListener(view -> {
                            switch (view.getId()) {
                                case R.id.ll_my_qrcode:
                                    toast("我的二维码");
                                    break;
                                case R.id.ll_setting:
                                    startActivity(SettingActivity.class);
                                    break;
                                default:
                                    break;
                            }
                        })
                        .setAutoDismiss(true)
                        .show();
                break;
            case R.id.ll_userinfo:
                startActivity(PersonalProfileActivity.class);
                break;
            case R.id.ll_follows:
                FollowFansActivity.start(getContext(), 0);
                break;
            case R.id.ll_fans:
                FollowFansActivity.start(getContext(), 1);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}