package com.roydon.niuyin.ui.popup;

import android.content.Context;

import com.hjq.base.BasePopupWindow;
import com.roydon.niuyin.R;

/**
 * desc   : 可进行拷贝的副本
 */
public final class CopyPopup {

    public static final class Builder extends BasePopupWindow.Builder<Builder> {

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.popup_copy);
        }
    }
}