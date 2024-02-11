package com.roydon.niuyin.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.base.action.AnimAction;
import com.hjq.toast.ToastUtils;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;

/**
 * @author roydon
 * @date 2024/2/10 13:23
 * @description niuyin-android
 * 视频评论弹窗
 */
@SuppressLint("MissingInflatedId")
public class VideoCommendDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> implements BaseDialog.OnShowListener, BaseDialog.OnCancelListener {

        OnListener mListener;

        private String commendText;

        private EditText mCommentContentET;
        private TextView mCommentLimitTV;
        private ImageView mSmileIV;
        private ImageView mAtIV;
        private Button mSendButton;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_video_commend);
            setAnimStyle(AnimAction.BOTTOM);
            setGravity(Gravity.BOTTOM);
            setOnClickListener(R.id.btn_send);

            mCommentContentET = findViewById(R.id.et_comment_content);
            mCommentLimitTV = findViewById(R.id.tv_comment_limit);
            mSmileIV = findViewById(R.id.iv_smile);
            mAtIV = findViewById(R.id.iv_at);
            mSendButton = findViewById(R.id.btn_send);

            mSendButton.setEnabled(false);

            addOnShowListener(this);
            addOnCancelListener(this);

            mCommentContentET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // 在文本变化前执行的操作，这里不需要处理
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        mSendButton.setEnabled(true);
                    }
                    // 在文本变化时执行的操作
                    int remainingChars = getResources().getInteger(R.integer.video_commend_limit_length) - s.length(); // 计算还可以输入的字数
                    mCommentLimitTV.setText(String.valueOf(remainingChars)); // 更新TextView显示
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 在文本变化后执行的操作，这里不需要处理
                }
            });
        }

        public Builder setListener(OnListener mListener) {
            this.mListener = mListener;
            return this;
        }

        public Builder setCommendText(String commendText) {
            this.commendText = commendText;
            return this;
        }

        /**
         * Dialog 显示了
         *
         * @param dialog
         */
        @Override
        public void onShow(BaseDialog dialog) {
            mCommentContentET.setText(commendText);
            mCommentContentET.setSelection(mCommentContentET.getText().length());
            mCommentContentET.setFocusable(true);
            mCommentContentET.setFocusableInTouchMode(true);
            mCommentContentET.requestFocus();
            postDelayed(() -> getSystemService(InputMethodManager.class).showSoftInput(mCommentContentET, 0), 300);
        }

        /**
         * Dialog 取消了
         *
         * @param dialog
         */
        @Override
        public void onCancel(BaseDialog dialog) {
            if (mListener != null) {
                mListener.onCancel(getDialog(), mCommentContentET.getText().toString());
            }
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_smile:
                    ToastUtils.show("smile");
                    break;
                case R.id.iv_at:
                    ToastUtils.show("at");
                    break;
                case R.id.btn_send:
                    if (mListener != null) {
                        mListener.onSend(getDialog(), mCommentContentET.getText().toString());
                    }
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnListener {

        /**
         * 发送回调
         *
         * @param dialog
         * @param content 评论内容
         */
        void onSend(BaseDialog dialog, String content);

        /**
         * 取消回调
         *
         * @param dialog
         * @param content 评论内容
         */
        void onCancel(BaseDialog dialog, String content);

    }

}