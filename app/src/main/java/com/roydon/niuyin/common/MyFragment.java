package com.roydon.niuyin.common;

import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseFragment;
import com.roydon.niuyin.action.TitleBarAction;
import com.roydon.niuyin.action.ToastAction;
import com.hjq.http.EasyHttp;
import com.hjq.umeng.UmengClient;
import com.roydon.niuyin.helper.SPUtils;

import butterknife.ButterKnife;

/**
 * desc   : 项目中 Fragment 懒加载基类
 */
public abstract class MyFragment<A extends MyActivity> extends BaseFragment<A> implements ToastAction, TitleBarAction {

    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    private boolean isLazyLoaded = false; // 标记是否已经懒加载过

    @Override
    protected void initFragment() {
        ButterKnife.bind(this, getView());

        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        initImmersion();

        // 如果是第一次初始化且可见，则进行懒加载
        if (!isLazyLoaded && getUserVisibleHint()) {
            lazyLoad();
        }

        super.initFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 如果可见且未懒加载过，则进行懒加载
        if (!isLazyLoaded && isVisibleToUser && isViewCreated()) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        // 进行懒加载操作
        lazyLoadData();
        isLazyLoaded = true;
    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoadData();

    private boolean isViewCreated() {
        return getView() != null;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig().init();

            // 设置标题栏沉浸
            if (mTitleBar != null) {
                ImmersionBar.setTitleBar(this, mTitleBar);
            }
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(statusBarDarkFont())
                // 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardEnable(true);
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        // 返回真表示黑色字体
        return true;
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = findTitleBar((ViewGroup) getView());
        }
        return mTitleBar;
    }

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return getAttachActivity().isShowDialog();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        getAttachActivity().showDialog();
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        getAttachActivity().hideDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 重新初始化状态栏
        statusBarConfig().init();
        UmengClient.onResume(this);
    }

    @Override
    public void onPause() {
        UmengClient.onPause(this);
        super.onPause();
    }

    @Override
    public void onDetach() {
        EasyHttp.cancel(this);
        super.onDetach();
    }

    public void spSetString(String key, String val) {
        SPUtils.putString(key, val, getContext());
    }

    public String spGetString(String key) {
        return SPUtils.getString(key, "", getContext());
    }
}