package com.roydon.niuyin.common;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseFragment;
import com.hjq.http.EasyHttp;
import com.hjq.umeng.UmengClient;
import com.roydon.niuyin.action.TitleBarAction;
import com.roydon.niuyin.action.ToastAction;

import butterknife.ButterKnife;

public abstract class LazyLoadFragment<A extends MyActivity> extends BaseFragment<A> implements ToastAction, TitleBarAction {
    private boolean isViewCreated = false; // 标记视图是否已创建
    private boolean isDataLoaded = false; // 标记数据是否已加载

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoadData(); // 在视图创建后进行数据加载
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isDataLoaded && isViewCreated && isVisibleToUser) {
            lazyLoadData(); // 在 Fragment 可见时进行数据加载
        }
    }

    private void lazyLoadData() {
        if (!isDataLoaded) {
            lazyLoad(); // 子类实现具体的数据加载逻辑
            isDataLoaded = true;
        }
    }

    public void lazyLoad(){
        initData();
    }

    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void initFragment() {
        ButterKnife.bind(this, getView());

        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        initImmersion();
        super.initFragment();
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
        if (!isDataLoaded && isViewCreated && getUserVisibleHint()) {
            lazyLoadData(); // 在 Fragment 可见时进行数据加载
        }
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
}
