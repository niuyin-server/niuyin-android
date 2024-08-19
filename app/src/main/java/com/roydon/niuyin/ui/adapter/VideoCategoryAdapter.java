package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.video.AppVideoCategoryVo;
import com.roydon.niuyin.http.response.video.MyVideoVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 20:11
 * @description niuyin-android
 */
public class VideoCategoryAdapter extends MyAdapter<AppVideoCategoryVo> {

    public VideoCategoryAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VideoCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoCategoryAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_category);
        }

        @BindView(R.id.iv_category_image)
        ImageView mCategoryImageView;
        @BindView(R.id.tv_category_name)
        TextView mCategoryNameView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            AppVideoCategoryVo item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCategoryImage() != null && !item.getCategoryImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCategoryImage()).into(mCategoryImageView);
            }
            mCategoryNameView.setText(item.getName());
        }
    }
}