package com.roydon.niuyin.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;

/**
 * @author roydon
 * @date 2024/1/30 20:59
 * @description niuyin-android
 */
public class SlideDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> implements BaseAdapter.OnItemClickListener {

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_slide);
        }

        @Override
        public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

        }
    }

}
