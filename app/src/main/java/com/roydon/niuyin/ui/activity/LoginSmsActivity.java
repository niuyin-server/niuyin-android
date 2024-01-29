package com.roydon.niuyin.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hjq.demo.wxapi.WXEntryActivity;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengClient;
import com.hjq.umeng.UmengLogin;
import com.hjq.widget.view.CountdownView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.InputTextHelper;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.GetCodeApi;
import com.roydon.niuyin.http.request.GetLoginAuthCodeApi;
import com.roydon.niuyin.http.request.LoginApi;
import com.roydon.niuyin.http.request.LoginSmsApi;
import com.roydon.niuyin.http.response.LoginBean;
import com.roydon.niuyin.other.CommonConstants;
import com.roydon.niuyin.other.KeyboardWatcher;

import butterknife.BindView;

public class LoginSmsActivity extends MyActivity implements UmengLogin.OnLoginListener, KeyboardWatcher.SoftKeyboardStateListener {

    @BindView(R.id.iv_login_logo)
    ImageView mLogoView;

    @BindView(R.id.ll_login_sms_body)
    LinearLayout mBodyLayout;

    @BindView(R.id.et_telephone)
    EditText mTelephoneView;
    @BindView(R.id.cv_login_countdown)
    CountdownView mLoginCountdownView;

    @BindView(R.id.et_login_auth_code)
    EditText mLoginAuthCodeView;

    @BindView(R.id.btn_login_sms_commit)
    Button mCommitView;

    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        mBodyLayout.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBodyLayout.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

            // 执行缩小动画
            mLogoView.setPivotX(mLogoView.getWidth() / 2f);
            mLogoView.setPivotY(mLogoView.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0.0f, -(keyboardHeight - bottom));
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        if (mLogoView.getTranslationY() == 0) {
            return;
        }
        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_sms;
    }

    @Override
    protected void initView() {
        InputTextHelper.with(this)
                .addView(mTelephoneView)
                .addView(mLoginAuthCodeView)
                .setMain(mCommitView)
                .setListener(helper -> mTelephoneView.getText().toString().length() == 11 && mLoginAuthCodeView.getText().toString().length() == 6)
                .build();

        setOnClickListener(R.id.tv_login_forget, R.id.cv_login_countdown, R.id.btn_login_sms_commit, R.id.iv_login_qq, R.id.iv_login_wx);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.cv_login_countdown:
                // 获取验证码
                if (mTelephoneView.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

                // 获取验证码
                EasyHttp.get(this)
                        .api(new GetLoginAuthCodeApi()
                                .setTelephone(mTelephoneView.getText().toString()))
                        .request(new HttpCallback<HttpData<String>>(this) {

                            @Override
                            public void onSucceed(HttpData<String> data) {
                                toast(R.string.common_code_send_hint);
                                mLoginCountdownView.start();
                            }

                            @Override
                            public void onFail(Exception e) {
                                super.onFail(e);
                                mLoginCountdownView.start();
                            }
                        });
                break;
            case R.id.btn_login_sms_commit:
                if (mTelephoneView.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

                EasyHttp.post(this)
                        .api(new LoginSmsApi()
                                .setTelephone(mTelephoneView.getText().toString())
                                .setSmsCode(mLoginAuthCodeView.getText().toString()))
                        .request(new HttpCallback<HttpData<LoginBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<LoginBean> data) {
                                toast("登录成功");
                                // 更新 Token
//                                EasyConfig.getInstance().addParam("token", data.getData().getToken());
                                EasyConfig.getInstance().addHeader(CommonConstants.AUTHORIZATION, CommonConstants.AUTHORIZATION_PREFIX + data.getData().getToken());
                                // 跳转到主页
                                startActivity(HomeActivity.class);
                                finish();
                            }

                            @Override
                            public void onFail(Exception e) {
                                EasyLog.print(e.toString());
                                toast("登录失败：" + e.getMessage());
                            }
                        });
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wx:
                toast("记得改好第三方 AppID 和 AppKey，否则会调不起来哦");
                Platform platform;
                switch (v.getId()) {
                    case R.id.iv_login_qq:
                        platform = Platform.QQ;
                        break;
                    case R.id.iv_login_wx:
                        platform = Platform.WECHAT;
                        toast("也别忘了改微信 " + WXEntryActivity.class.getSimpleName() + " 类所在的包名哦");
                        break;
                    default:
                        throw new IllegalStateException("are you ok?");
                }
                UmengClient.login(this, platform, this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
        UmengClient.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * 第三方登录成功
     *
     * @param platform 平台名称
     * @param data     用户资料返回
     */
    @Override
    public void onSucceed(Platform platform, UmengLogin.LoginData data) {
        // 判断第三方登录的平台
        switch (platform) {
            case QQ:
                break;
            case WECHAT:
                break;
            default:
                break;
        }

        GlideApp.with(this)
                .load(data.getAvatar())
                .circleCrop()
                .into(mLogoView);

        toast("昵称：" + data.getName() + "\n" + "性别：" + data.getSex());
        toast("id：" + data.getId());
        toast("token：" + data.getToken());
    }
}