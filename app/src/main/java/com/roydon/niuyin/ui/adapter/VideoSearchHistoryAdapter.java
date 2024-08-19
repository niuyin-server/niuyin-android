package com.roydon.niuyin.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.response.search.VideoSearchHistory;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/3 21:45
 * @description 视频搜索历史适配器
 */
public class VideoSearchHistoryAdapter extends MyAdapter<VideoSearchHistory> {

    public VideoSearchHistoryAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VideoSearchHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoSearchHistoryAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_search_history);
        }

        @BindView(R.id.tv_history_keyword)
        TextView mKeywordView;

        @Override
        public void onBindView(int position) {
            VideoSearchHistory item = getItem(position);
            mKeywordView.setText(item.getKeyword());
        }
    }
}