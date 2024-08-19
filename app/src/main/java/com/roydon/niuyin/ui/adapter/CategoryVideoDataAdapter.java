package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.video.CategoryVideoVo;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 14:42
 * @description 分类视频适配器
 */
public class CategoryVideoDataAdapter extends MyAdapter<CategoryVideoVo> {

    public CategoryVideoDataAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_category_video_data);
        }

        @BindView(R.id.iv_cover)
        ImageView mCoverView;
        @BindView(R.id.tv_title)
        TextView mTitleView;
        @BindView(R.id.iv_author_avatar)
        ImageView mAvatarView;
        @BindView(R.id.tv_author_nickname)
        TextView mNicknameView;
        @BindView(R.id.iv_video_type)
        ImageView mVideoTypeView;
        @BindView(R.id.tv_view_num)
        TextView mViewNumView;
        @BindView(R.id.tv_like_num)
        TextView mLikeNumView;

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            CategoryVideoVo item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            mLikeNumView.setText(item.getLikeNum().toString());
            mTitleView.setText(item.getVideoTitle());
            mViewNumView.setText(item.getViewNum().toString());
            if ((PublishType.VIDEO.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if ((PublishType.IMAGE.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            if (item.getAuthor() == null) {
                return;
            }
            if (item.getAuthor().getAvatar() != null && !item.getAuthor().getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAuthor().getAvatar()).into(mAvatarView);
            }
            mNicknameView.setText(item.getAuthor().getNickName());
        }
    }
}