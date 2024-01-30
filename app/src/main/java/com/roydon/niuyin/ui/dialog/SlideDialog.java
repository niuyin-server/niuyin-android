package com.roydon.niuyin.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;

/**
 * @author roydon
 * @date 2024/1/30 20:59
 * @description niuyin-android
 */
@SuppressLint("NonConstantResourceId")
public class SlideDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        private boolean mAutoDismiss = true;
        View.OnClickListener mListener;

        @SuppressLint("ClickableViewAccessibility")
        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_slide);
            setOnClickListener(R.id.ll_my_qrcode, R.id.ll_setting);
        }

        public Builder setListener(View.OnClickListener mListener) {
            this.mListener = mListener;
            return this;
        }

        public Builder setAutoDismiss(boolean mAutoDismiss) {
            this.mAutoDismiss = mAutoDismiss;
            return this;
        }

        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                dismiss();
            }
            if (mListener != null) {
                mListener.onClick(v);

            }
        }
    }

}
