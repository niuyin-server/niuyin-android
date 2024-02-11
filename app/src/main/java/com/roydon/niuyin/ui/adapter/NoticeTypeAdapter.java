package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.domain.NoticeTypeBean;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 20:11
 * @description 消息类型适配器
 */
public class NoticeTypeAdapter extends MyAdapter<NoticeTypeBean> {

    public NoticeTypeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public NoticeTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeTypeAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_notice_type);
        }

        @BindView(R.id.iv_notice_icon)
        ImageView mNoticeIconView;
        @BindView(R.id.tv_notice_name)
        TextView mNoticeNameView;
        @BindView(R.id.tv_notice_desc)
        TextView mNoticeDescView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            NoticeTypeBean item = getItem(position);
            if (item == null) {
                return;
            }
            mNoticeIconView.setImageResource(item.getIcon());
            mNoticeNameView.setText(item.getName());
            mNoticeDescView.setText(item.getDesc());
        }
    }
}