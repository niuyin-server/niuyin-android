package com.roydon.niuyin.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.InputTextHelper;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.GetCodeApi;
import com.roydon.niuyin.http.request.RegisterApi;
import com.roydon.niuyin.http.request.user.SmsRegisterApi;
import com.roydon.niuyin.http.response.RegisterBean;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;

import butterknife.BindView;

/**
 * desc   : 注册界面
 */
public final class RegisterActivity extends MyActivity {

    @BindView(R.id.tv_register_title)
    TextView mTitleView;

    @BindView(R.id.et_register_phone)
    EditText mPhoneView;
    @BindView(R.id.cv_register_countdown)
    CountdownView mCountdownView;

    @BindView(R.id.et_register_code)
    EditText mCodeView;

    @BindView(R.id.et_register_password1)
    EditText mPasswordView1;
    @BindView(R.id.et_register_password2)
    EditText mPasswordView2;

    @BindView(R.id.btn_register_commit)
    Button mCommitView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        // 给这个 View 设置沉浸式，避免状态栏遮挡
        ImmersionBar.setTitleBar(this, mTitleView);

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mPasswordView1)
                .addView(mPasswordView2)
                .setMain(mCommitView)
                .setListener(helper -> mPhoneView.getText().toString().length() == 11 &&
                        mPasswordView1.getText().toString().length() >= 6 &&
                        mPasswordView1.getText().toString().length() <= 18 &&
                        mPasswordView1.getText().toString().equals(mPasswordView2.getText().toString()))
                .build();

        setOnClickListener(R.id.cv_register_countdown, R.id.btn_register_commit);
    }

    @Override
    protected void initData() {

    }

    @Override
    public ImmersionBar createStatusBarConfig() {
        // 不要把整个布局顶上去
        return super.createStatusBarConfig().keyboardEnable(true);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_register_countdown:
                // 获取验证码
                if (mPhoneView.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

                if (true) {
                    toast(R.string.common_code_send_hint);
                    mCountdownView.start();
                    return;
                }

                // 获取验证码
//                EasyHttp.post(this)
//                        .api(new GetCodeApi()
//                                .setPhone(mPhoneView.getText().toString()))
//                        .request(new HttpCallback<HttpData<Void>>(this) {
//
//                            @Override
//                            public void onSucceed(HttpData<Void> data) {
//                                toast(R.string.common_code_send_hint);
//                                mCountdownView.start();
//                            }
//
//                            @Override
//                            public void onFail(Exception e) {
//                                super.onFail(e);
//                                mCountdownView.start();
//                            }
//                        });
                break;
            case R.id.btn_register_commit:
                if (true) {
                    LoginActivity.start(getActivity(), mPhoneView.getText().toString(), mPasswordView1.getText().toString());
                    setResult(RESULT_OK);
                    finish();
                    return;
                }
                // 提交注册
                EasyHttp.post(this)
                        .api(new SmsRegisterApi()
                                .setTelephone(mPhoneView.getText().toString())
                                .setSmsCode(mCodeView.getText().toString())
                                .setPassword(mPasswordView1.getText().toString())
                                .setConfirmPassword(mPasswordView2.getText().toString()))
                        .request(new HttpCallback<HttpData<Boolean>>(this) {

                            @Override
                            public void onSucceed(HttpData<Boolean> data) {
                                LoginActivity.start(getActivity(), mPhoneView.getText().toString(), mPasswordView1.getText().toString());
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}