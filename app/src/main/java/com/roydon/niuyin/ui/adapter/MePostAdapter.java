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
import com.roydon.niuyin.http.response.MyVideoVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 20:11
 * @description niuyin-android
 */
public class MePostAdapter extends MyAdapter<MyVideoVO> {

    public MePostAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public MePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MePostAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_me_post);
        }

        @BindView(R.id.iv_cover)
        ImageView mCoverView;
        @BindView(R.id.iv_video_type)
        ImageView mVideoTypeView;
        @BindView(R.id.tv_like_num)
        TextView mLikeNumView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            MyVideoVO item = getItem(position);
            if (item.getCoverImage() != null || !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            if (item.getPublishType().equals(PublishType.VIDEO.getCode())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            mLikeNumView.setText(item.getLikeNum().toString());
        }
    }
}