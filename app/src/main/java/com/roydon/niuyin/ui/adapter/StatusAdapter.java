package com.roydon.niuyin.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;

import butterknife.BindView;

/**
 *    desc   : 状态数据列表
 */
public final class StatusAdapter extends MyAdapter<String> {

    public StatusAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        @BindView(R.id.tv_status_text)
        TextView mTextView;

        ViewHolder() {
            super(R.layout.item_status);
        }

        @Override
        public void onBindView(int position) {
            mTextView.setText(getItem(position));
        }
    }
}