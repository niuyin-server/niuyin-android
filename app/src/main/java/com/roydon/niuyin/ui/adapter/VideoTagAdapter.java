package com.roydon.niuyin.ui.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;

import butterknife.BindView;

/**
 * desc: 视频标签适配器
 */
public final class VideoTagAdapter extends MyAdapter<String> {

    public VideoTagAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_tag);
        }

        @BindView(R.id.tv_tag)
        TextView mTagView;

        @Override
        public void onBindView(int position) {
            String item = getItem(position);
            mTagView.setText(item);
        }
    }
}