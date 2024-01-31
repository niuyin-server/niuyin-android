package com.roydon.niuyin.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.roydon.niuyin.http.response.MemberInfoVO;
import com.roydon.niuyin.ui.activity.BrowserActivity;
import com.roydon.niuyin.ui.activity.DialogActivity;
import com.roydon.niuyin.ui.activity.GuideActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.ImageActivity;
import com.roydon.niuyin.ui.activity.PasswordResetActivity;
import com.roydon.niuyin.ui.activity.PersonalProfileActivity;
import com.roydon.niuyin.ui.activity.PhoneResetActivity;
import com.roydon.niuyin.ui.activity.SettingActivity;
import com.roydon.niuyin.ui.activity.StatusActivity;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.ui.adapter.MeAdapter;
import com.roydon.niuyin.ui.dialog.MessageDialog;
import com.roydon.niuyin.ui.dialog.SlideDialog;
import com.roydon.niuyin.ui.fragment.index.IndexFollowFragment;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.ui.fragment.me.MeFavoriteFragment;
import com.roydon.niuyin.ui.fragment.me.MeLikeFragment;
import com.roydon.niuyin.ui.fragment.me.MePostFragment;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
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
    ImageView mMenuSearchView;
    @BindView(R.id.iv_menu_list)
    ImageView mMenuListView;
    @BindView(R.id.ll_userinfo)
    LinearLayout mUserinfoView;

    // 用户信息
    @BindView(R.id.riv_avatar)
    ImageView mAvatarView;
    @BindView(R.id.tv_nickname)
    TextView mNickNameView;
    @BindView(R.id.tv_menu_nickname)
    TextView mMenuNickNameView;
    @BindView(R.id.tv_user_id)
    TextView mUserIdView;

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

        setOnClickListener(R.id.iv_menu_list, R.id.ll_userinfo);

    }

    @Override
    protected void initData() {
        EasyHttp.get(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<MemberInfoVO>>(this.getAttachActivity()) {

                    @Override
                    public void onSucceed(HttpData<MemberInfoVO> data) {
//                        toast("成功" + data.getData().toString());
                        memberInfoVO = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_GET_USERINFO);
                        // 更新缓存
                        SPUtils.putString(SPUtils.AVATAR, memberInfoVO.getAvatar(), getContext());
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
        }
        mNickNameView.setText(memberInfoVO.getNickName());
        mMenuNickNameView.setText(memberInfoVO.getNickName());
        mMenuNickNameView.setVisibility(View.GONE);
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
            GlideApp.with(this)
                    .load(memberInfoVO.getAvatar())
                    .circleCrop()
                    .into(mMenuAvatarView);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.black));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuSearchView.setImageResource(R.drawable.ic_search_b);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_transparent);
            mMenuListView.setImageResource(R.drawable.ic_list_b);
            mMenuNickNameView.setVisibility(View.VISIBLE);
        } else {
            getStatusBarConfig().statusBarDarkFont(false).init();
            mUserinfoView.setVisibility(View.VISIBLE);
            mMenuAvatarView.setVisibility(View.GONE);
            mMenuNicknameView.setTextColor(ContextCompat.getColor(getAttachActivity(), R.color.white));
            mMenuSearchView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuSearchView.setImageResource(R.drawable.ic_search_w);
            mMenuListView.setBackgroundResource(R.drawable.bg_icon_grey);
            mMenuListView.setImageResource(R.drawable.ic_list_w);
            mMenuNickNameView.setVisibility(View.GONE);
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