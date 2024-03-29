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
import com.roydon.niuyin.http.response.video.VideoRecommendVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 14:42
 * @description 首页推荐视频适配器
 */
public class RecommendVideoAdapter extends MyAdapter<VideoRecommendVO> {

    public RecommendVideoAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_recommend_video);
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
        @BindView(R.id.tv_comment_num)
        TextView mCommentNumView;

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            VideoRecommendVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            mTitleView.setText(item.getVideoTitle());
            if (item.getAuthor().getAvatar() != null && !item.getAuthor().getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAuthor().getAvatar()).into(mAvatarView);
            }
            mNicknameView.setText(item.getAuthor().getNickName());
            if ((PublishType.VIDEO.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if ((PublishType.IMAGE.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            mViewNumView.setText(item.getViewNum() + "");
            mLikeNumView.setText(item.getLikeNum() + "");
            mCommentNumView.setText(item.getCommentNum() + "");
        }
    }
}