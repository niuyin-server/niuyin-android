package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.base.BaseFragmentAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.ActivityStackManager;
import com.roydon.niuyin.helper.DoubleClickHelper;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.notice.UnreadNoticeCountApi;
import com.roydon.niuyin.other.KeyboardWatcher;
import com.roydon.niuyin.ui.fragment.FragmentUpload;
import com.roydon.niuyin.ui.fragment.FragmentIndex;
import com.roydon.niuyin.ui.fragment.FragmentFriend;
import com.roydon.niuyin.ui.fragment.FragmentMessage;
import com.roydon.niuyin.ui.fragment.FragmentMe;

import java.util.Objects;

import butterknife.BindView;

/**
 * desc   : 主页界面
 */
public final class HomeActivity extends MyActivity implements KeyboardWatcher.SoftKeyboardStateListener, BottomNavigationView.OnNavigationItemSelectedListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_NOTICE_UNREAD_COUNT = 1;
    private static final int HANDLER_NOTICE_UNREAD_COUNT_ERROR = 2;

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private BaseFragmentAdapter<MyFragment> mPagerAdapter;

    // 添加徽章 badge
    BadgeDrawable badge;
    /**
     * 未读消息数
     */
    private Long noticeUnreadCount = 0L;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        KeyboardWatcher.with(this).setListener(this);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(FragmentIndex.newInstance());
        mPagerAdapter.addFragment(FragmentFriend.newInstance());
        mPagerAdapter.addFragment(FragmentUpload.newInstance());
        mPagerAdapter.addFragment(FragmentMessage.newInstance());
        mPagerAdapter.addFragment(FragmentMe.newInstance());

        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());

        // 添加徽章 badge
        badge = mBottomNavigationView.getOrCreateBadge(R.id.home_message);

        // 设置徽章的显示位置
        badge.setVerticalOffset(15);
//        badge.setHorizontalOffset(10);
        // 设置徽章的样式
        badge.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryRed));
        badge.setBadgeTextColor(ContextCompat.getColor(this, R.color.white));
        badge.setMaxCharacterCount(3);

        getNoticeUnReadCount();
    }

    /**
     * 获取未读消息数
     */
    private void getNoticeUnReadCount() {
        EasyHttp.get(this)
                .api(new UnreadNoticeCountApi())
                .request(new HttpCallback<HttpData<Long>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<Long> data) {
                        if (Objects.isNull(data) || data.getData().equals(0L)) {
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                            return;
                        }
                        noticeUnreadCount = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_NOTICE_UNREAD_COUNT);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mHandler.sendEmptyMessage(HANDLER_NOTICE_UNREAD_COUNT_ERROR);
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
                    badge.clearNumber();
                    break;
                case HANDLER_NOTICE_UNREAD_COUNT:
                    badge.setNumber(noticeUnreadCount.intValue());
                    break;
                case HANDLER_NOTICE_UNREAD_COUNT_ERROR:
//                    toast("加载失败");
                    badge.setVisible(false);
                    badge.clearNumber();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                mPagerAdapter.setCurrentItem(FragmentIndex.class);
                return true;
            case R.id.home_friend:
                mPagerAdapter.setCurrentItem(FragmentFriend.class);
                return true;
            case R.id.home_upload:
                mPagerAdapter.setCurrentItem(FragmentUpload.class);
                return true;
            case R.id.home_message:
                mPagerAdapter.setCurrentItem(FragmentMessage.class);
                return true;
            case R.id.home_me:
                mPagerAdapter.setCurrentItem(FragmentMe.class);
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */
    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        mBottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onSoftKeyboardClosed() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 回调当前 Fragment 的 onKeyDown 方法
        if (mPagerAdapter.getCurrentFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            // 移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(() -> {

                // 进行内存优化，销毁掉所有的界面
                ActivityStackManager.getInstance().finishAllActivities();
                // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
                // System.exit(0);

            }, 300);
        } else {
            toast(R.string.home_exit_hint);
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}