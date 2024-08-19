package com.roydon.niuyin.ui.activity;

import android.view.Gravity;
import android.view.View;

import com.hjq.base.BaseDialog;
import com.hjq.base.action.AnimAction;
import com.hjq.http.EasyConfig;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.ActivityStackManager;
import com.roydon.niuyin.helper.CacheDataManager;
import com.roydon.niuyin.helper.SPManager;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.helper.TokenManager;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.LogoutApi;
import com.roydon.niuyin.other.AppConfig;
import com.roydon.niuyin.other.CommonConstants;
import com.roydon.niuyin.ui.dialog.MenuDialog;
import com.roydon.niuyin.ui.dialog.MessageDialog;
import com.roydon.niuyin.ui.dialog.UpdateDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.SwitchButton;

import butterknife.BindView;

/**
 * desc   : 设置界面
 */
public final class SettingActivity extends MyActivity
        implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.sb_setting_cache)
    SettingBar mCleanCacheView;

    @BindView(R.id.sb_setting_switch)
    SwitchButton mAutoSwitchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        // 设置切换按钮的监听
        mAutoSwitchView.setOnCheckedChangeListener(this);

        setOnClickListener(R.id.sb_setting_language, R.id.sb_setting_update, R.id.sb_setting_agreement, R.id.sb_setting_about,
                R.id.sb_setting_cache, R.id.sb_setting_auto, R.id.sb_setting_exit, R.id.sb_setting_author, R.id.sb_setting_web, R.id.sb_setting_creator);
    }

    @Override
    protected void initData() {
        // 获取应用缓存大小
        mCleanCacheView.setRightText(CacheDataManager.getTotalCacheSize(this));
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_setting_language:
                // 底部选择框
                new MenuDialog.Builder(this)
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setList(R.string.setting_language_simple, R.string.setting_language_complex)
                        .setListener((MenuDialog.OnListener<String>) (dialog, position, string) -> BrowserActivity.start(getActivity(), "https://github.com/getActivity/MultiLanguages"))
                        .setGravity(Gravity.BOTTOM)
                        .setAnimStyle(AnimAction.BOTTOM)
                        .show();
                break;
            case R.id.sb_setting_update:
                // 本地的版本码和服务器的进行比较
                if (20 > AppConfig.getVersionCode()) {

                    new UpdateDialog.Builder(this)
                            // 版本名
                            .setVersionName("2.0")
                            // 是否强制更新
                            .setForceUpdate(false)
                            // 更新日志
                            .setUpdateLog("修复Bug\n优化用户体验")
                            // 下载 url
                            .setDownloadUrl("https://raw.githubusercontent.com/getActivity/AndroidProject/master/AndroidProject.apk")
                            .show();
                } else {
                    toast(R.string.update_no_update);
                }
                break;
            case R.id.sb_setting_author:
                BrowserActivity.start(this, getString(R.string.author_link));
                break;
            case R.id.sb_setting_web:
                BrowserActivity.start(this, getString(R.string.niuyin_web_link));
                break;
            case R.id.sb_setting_creator:
                BrowserActivity.start(this, getString(R.string.niuyin_creator_link));
                break;
            case R.id.sb_setting_agreement:
                BrowserActivity.start(this, getString(R.string.author_link));
                break;
            case R.id.sb_setting_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.sb_setting_auto:
                // 自动登录
                mAutoSwitchView.setChecked(!mAutoSwitchView.isChecked());
                break;
            case R.id.sb_setting_cache:
                // 清除内存缓存（必须在主线程）
                GlideApp.get(getActivity()).clearMemory();
                new Thread(() -> {
                    // 清除本地缓存（必须在子线程）
                    GlideApp.get(getActivity()).clearDiskCache();
                }).start();
                CacheDataManager.clearAllCache(this);
                postDelayed(() -> {
                    // 重新获取应用缓存大小
                    mCleanCacheView.setRightText(CacheDataManager.getTotalCacheSize(getActivity()));
                }, 500);
                break;
            case R.id.sb_setting_exit:
                new MessageDialog.Builder(this)
                        .setTitle("退出登录")
                        .setMessage("确定退出登录吗？确定后将清空当前登录信息")
                        .setConfirm(getString(R.string.common_confirm))
                        .setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                // 清除token
                                TokenManager.getInstance(getActivity()).clearToken();
                                // 清除用户缓存
                                SPManager.getInstance(getActivity()).clear();
                                // 进行内存优化，销毁除登录页之外的所有界面
                                ActivityStackManager.getInstance().finishAllActivities();
                                startActivity(LoginActivity.class);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();

                // 退出登录
//                EasyHttp.post(this)
//                        .api(new LogoutApi())
//                        .request(new HttpCallback<HttpData<Void>>(this) {
//
//                            @Override
//                            public void onSucceed(HttpData<Void> data) {
//                                // 清除token
//                                TokenManager.clearToken(getActivity());
//                                // 清除用户缓存
//                                SPUtils.remove(SPUtils.AVATAR, getActivity());
//                                startActivity(LoginActivity.class);
//                                // 进行内存优化，销毁除登录页之外的所有界面
//                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
//                            }
//                        });
                break;
            default:
                break;
        }
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}