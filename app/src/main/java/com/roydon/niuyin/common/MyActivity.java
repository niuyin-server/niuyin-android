package com.roydon.niuyin.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseActivity;
import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.SwipeAction;
import com.roydon.niuyin.action.TitleBarAction;
import com.roydon.niuyin.action.ToastAction;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.ui.dialog.WaitDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.umeng.UmengClient;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends BaseActivity implements ToastAction, TitleBarAction, SwipeAction, OnHttpListener {

    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    /**
     * 加载对话框
     */
    private BaseDialog mDialog;
    /**
     * 对话框数量
     */
    private int mDialogTotal;

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 显示加载对话框 todo 禁用网络加载框，后续取消注释
     */
    public void showDialog() {
//        if (mDialog == null) {
//            mDialog = new WaitDialog.Builder(this).setCancelable(false).create();
//        }
//        if (!mDialog.isShowing()) {
//            mDialog.show();
//        }
        mDialogTotal++;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal == 1) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }
    }

    @Override
    protected void initLayout() {
        super.initLayout();

        if (getTitleBar() != null) {
            getTitleBar().setOnTitleBarListener(this);
        }

        ButterKnife.bind(this);
        initImmersion();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            createStatusBarConfig().init();

            // 设置标题栏沉浸
            if (mTitleBar != null) {
                ImmersionBar.setTitleBar(this, mTitleBar);
            }
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected ImmersionBar createStatusBarConfig() {
        // 在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont());
        return mImmersionBar;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @Nullable
    public ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(@StringRes int id) {
        setTitle(getString(id));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
        }
    }

    @Override
    @Nullable
    public TitleBar getTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = findTitleBar(getContentView());
        }
        return mTitleBar;
    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengClient.onResume(this);
    }

    @Override
    protected void onPause() {
        UmengClient.onPause(this);
        super.onPause();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * {@link OnHttpListener}
     */
    @Override
    public void onStart(Call call) {
        showDialog();
    }

    @Override
    public void onSucceed(Object result) {
        if (result instanceof HttpData) {
            toast(((HttpData) result).getMessage());
        }
    }

    @Override
    public void onFail(Exception e) {
        toast(e.getMessage());
    }

    @Override
    public void onEnd(Call call) {
        hideDialog();
    }

    @Override
    protected void onDestroy() {
        EasyHttp.cancel(this);
        if (isShowDialog()) {
            mDialog.dismiss();
        }
        mDialog = null;
        super.onDestroy();
    }

    public void spSetString(String key, String val) {
        SPUtils.putString(key, val, this);
    }

    public String spGetString(String key) {
        return SPUtils.getString(key, "", this);
    }
}