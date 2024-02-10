package com.roydon.niuyin.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.base.action.AnimAction;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;

/**
 * @author roydon
 * @date 2024/2/10 13:23
 * @description niuyin-android
 * 视频评论弹窗
 */
public class VideoCommendDialog {

    public static final class Builder extends BaseDialog.Builder<Builder>  implements BaseDialog.OnShowListener, BaseDialog.OnCancelListener{

        OnListener mListener;

        private EditText mCommentContentET;
        private TextView mCommentLimitTV;
        private Button mSendButton;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_video_commend);
            setAnimStyle(AnimAction.BOTTOM);
            setGravity(Gravity.BOTTOM);
            setOnClickListener(R.id.btn_send);

            mCommentContentET = findViewById(R.id.et_comment_content);
            mCommentLimitTV = findViewById(R.id.tv_comment_limit);
            mSendButton = findViewById(R.id.btn_send);

            mCommentContentET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // 在文本变化前执行的操作，这里不需要处理
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
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

        /**
         * Dialog 显示了
         *
         * @param dialog
         */
        @Override
        public void onShow(BaseDialog dialog) {
            mCommentContentET.setFocusable(true);
            mCommentContentET.setFocusableInTouchMode(true);
            mCommentContentET.requestFocus();
            postDelayed(() -> getSystemService(InputMethodManager.class).showSoftInput(mCommentContentET, 0), 500);
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
         */
        void onSend(BaseDialog dialog, String content);
        void onCancel(BaseDialog dialog, String content);

    }

}