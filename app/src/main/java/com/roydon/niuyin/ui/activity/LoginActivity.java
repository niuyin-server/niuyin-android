package com.roydon.niuyin.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.InputTextHelper;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.helper.TokenManager;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.LoginApi;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.response.LoginBean;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.other.CommonConstants;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.other.KeyboardWatcher;
import com.hjq.demo.wxapi.WXEntryActivity;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengClient;
import com.hjq.umeng.UmengLogin;
import com.roydon.niuyin.ui.dialog.WaitDialog;

import butterknife.BindView;

/**
 * desc   : 登录界面
 */
public final class LoginActivity extends MyActivity implements UmengLogin.OnLoginListener, KeyboardWatcher.SoftKeyboardStateListener {

    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }

    @BindView(R.id.iv_login_logo)
    ImageView mLogoView;

    @BindView(R.id.ll_login_body)
    LinearLayout mBodyLayout;
    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;

    @BindView(R.id.tv_login_sms)
    TextView mLoginSmsView;

    @BindView(R.id.btn_login_commit)
    Button mCommitView;

    @BindView(R.id.v_login_blank)
    View mBlankView;

    @BindView(R.id.ll_login_other)
    View mOtherView;
    @BindView(R.id.iv_login_qq)
    View mQQView;
    @BindView(R.id.iv_login_wx)
    View mWeChatView;

    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .setListener(helper -> mPhoneView.getText().toString().length() == 11 && mPasswordView.getText().toString().length() >= 6)
                .build();

        setOnClickListener(R.id.tv_login_sms, R.id.tv_login_forget, R.id.btn_login_commit, R.id.iv_login_qq, R.id.iv_login_wx);
    }

    @Override
    protected void initData() {

        postDelayed(() -> {
            // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
            if (mBlankView.getHeight() > mBodyLayout.getHeight()) {
                // 只有空白区域的高度大于登录框区域的高度才展示动画
                KeyboardWatcher.with(LoginActivity.this)
                        .setListener(LoginActivity.this);
            }
        }, 500);

        // 判断用户当前有没有安装 QQ
        if (!UmengClient.isAppInstalled(this, Platform.QQ)) {
            mQQView.setVisibility(View.GONE);
        }

        // 判断用户当前有没有安装微信
        if (!UmengClient.isAppInstalled(this, Platform.WECHAT)) {
            mWeChatView.setVisibility(View.GONE);
        }

        // 如果这两个都没有安装就隐藏提示
        if (mQQView.getVisibility() == View.GONE && mWeChatView.getVisibility() == View.GONE) {
            mOtherView.setVisibility(View.GONE);
        }

        // 填充传入的手机号和密码
        mPhoneView.setText(getString(IntentKey.PHONE));
        mPasswordView.setText(getString(IntentKey.PASSWORD));
    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivityForResult(RegisterActivity.class, (resultCode, data) -> {
            // 如果已经注册成功，就执行登录操作
            if (resultCode == RESULT_OK && data != null) {
                mPhoneView.setText(data.getStringExtra(IntentKey.PHONE));
                mPasswordView.setText(data.getStringExtra(IntentKey.PASSWORD));
                onClick(mCommitView);
            }
        });
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_sms:
                startActivity(LoginSmsActivity.class);
                break;
            case R.id.tv_login_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.btn_login_commit:
                if (mPhoneView.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

//                if (true) {
//                    startActivity(HomeActivity.class);
//                    finish();
//                    return;
//                }
                new WaitDialog.Builder(this).setCancelable(true).create().show();
                apiLogin(mPhoneView.getText().toString(), mPasswordView.getText().toString());
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

    /**
     * 手机密码登录
     *
     * @param phone
     * @param pwd
     */
    private void apiLogin(String phone, String pwd) {
        EasyHttp.post(this)
                .api(new LoginApi()
                        .setUsername(phone)
                        .setPassword(pwd))
                .request(new HttpCallback<HttpData<LoginBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<LoginBean> data) {
                        toast("登录成功");
                        apiGetUserInfo();
                        // 更新 Token
//                                EasyConfig.getInstance().addParam("token", data.getData().getToken());
                        EasyConfig.getInstance().addHeader(CommonConstants.AUTHORIZATION, CommonConstants.AUTHORIZATION_PREFIX + data.getData().getToken());
                        // token保存到本地
                        TokenManager.getInstance(getActivity()).saveToken(data.getData().getToken());
                        // 跳转到主页
                        startActivity(HomeActivity.class);
                        finish();

                    }
                });
    }

    private void apiGetUserInfo() {
        EasyHttp.get(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<MemberInfoVO>>(this) {

                    @Override
                    public void onSucceed(HttpData<MemberInfoVO> data) {
                        MemberInfoVO memberInfoVO = data.getData();
                        // 更新缓存
                        spSetString(SPUtils.AVATAR, memberInfoVO.getAvatar());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
        UmengClient.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * {@link UmengLogin.OnLoginListener}
     */

    /**
     * 授权成功的回调
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

    /**
     * 授权失败的回调
     *
     * @param platform 平台名称
     * @param t        错误原因
     */
    @Override
    public void onError(Platform platform, Throwable t) {
        toast("第三方登录出错：" + t.getMessage());
    }

    /**
     * 授权取消的回调
     *
     * @param platform 平台名称
     */
    @Override
    public void onCancel(Platform platform) {
        toast("取消第三方登录");
    }

    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */

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
    public boolean isSwipeEnable() {
        return false;
    }
}