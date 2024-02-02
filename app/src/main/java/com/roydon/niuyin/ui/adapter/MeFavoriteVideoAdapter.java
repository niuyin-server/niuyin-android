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
import com.roydon.niuyin.http.response.behave.MyFavoriteVideoVO;
import com.roydon.niuyin.http.response.behave.MyLikeVideoVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/2 20:50
 * @description niuyin-android
 */
public class MeFavoriteVideoAdapter extends MyAdapter<MyFavoriteVideoVO> {

    public MeFavoriteVideoAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public MeFavoriteVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeFavoriteVideoAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_me_favorite_video);
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
            MyFavoriteVideoVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            if ((PublishType.VIDEO.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if ((PublishType.IMAGE.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            mLikeNumView.setText(item.getLikeNum().toString());
        }
    }
}