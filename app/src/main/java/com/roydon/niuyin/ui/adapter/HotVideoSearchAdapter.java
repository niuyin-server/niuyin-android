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
 * @description 热搜适配器
 */
public class HotVideoSearchAdapter extends MyAdapter<String> {

    public HotVideoSearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HotVideoSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotVideoSearchAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_hot_video_search);
        }

        @BindView(R.id.tv_sort)
        TextView mSortView;
        @BindView(R.id.tv_keyword)
        TextView mKeywordView;

        @Override
        public void onBindView(int position) {
            String item = getItem(position);
            mSortView.setText(position + 1 + "");
            mKeywordView.setText(item);
        }
    }
}