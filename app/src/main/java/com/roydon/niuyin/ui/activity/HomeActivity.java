package com.roydon.niuyin.ui.activity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.base.BaseFragmentAdapter;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.ActivityStackManager;
import com.roydon.niuyin.helper.DoubleClickHelper;
import com.roydon.niuyin.other.KeyboardWatcher;
import com.roydon.niuyin.ui.fragment.FragmentUpload;
import com.roydon.niuyin.ui.fragment.IndexFragment;
import com.roydon.niuyin.ui.fragment.FriendFragment;
import com.roydon.niuyin.ui.fragment.MessageFragment;
import com.roydon.niuyin.ui.fragment.FragmentMe;

import butterknife.BindView;

/**
 * desc   : 主页界面
 */
public final class HomeActivity extends MyActivity implements KeyboardWatcher.SoftKeyboardStateListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private BaseFragmentAdapter<MyFragment> mPagerAdapter;

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
        mPagerAdapter.addFragment(IndexFragment.newInstance());
        mPagerAdapter.addFragment(FriendFragment.newInstance());
        mPagerAdapter.addFragment(FragmentUpload.newInstance());
        mPagerAdapter.addFragment(MessageFragment.newInstance());
        mPagerAdapter.addFragment(FragmentMe.newInstance());

        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                mPagerAdapter.setCurrentItem(IndexFragment.class);
                return true;
            case R.id.home_friend:
                mPagerAdapter.setCurrentItem(FriendFragment.class);
                return true;
            case R.id.home_upload:
                mPagerAdapter.setCurrentItem(FragmentUpload.class);
                return true;
            case R.id.home_message:
                mPagerAdapter.setCurrentItem(MessageFragment.class);
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