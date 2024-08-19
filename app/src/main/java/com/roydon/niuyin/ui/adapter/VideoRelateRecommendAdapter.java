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
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.social.DynamicUser;
import com.roydon.niuyin.http.response.video.RelateVideoVO;

import butterknife.BindView;

/**
 * 视频相关推荐
 */
public final class VideoRelateRecommendAdapter extends MyAdapter<RelateVideoVO> {

    public VideoRelateRecommendAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_relate_recommend);
        }

        @BindView(R.id.iv_cover)
        ImageView mCoverIV;
        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.iv_video_type)
        ImageView mVideoTypeIV;
        @BindView(R.id.tv_view_num)
        TextView mViewNumTV;
        @BindView(R.id.tv_like_num)
        TextView mLikeNumTV;
        @BindView(R.id.tv_comment_num)
        TextView mCommentNumTV;
        @BindView(R.id.iv_author_avatar)
        ImageView mAuthorAvatarTV;
        @BindView(R.id.tv_author_nickname)
        TextView mAuthorNicknameTV;


        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            RelateVideoVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverIV);
            }
            mTitleTV.setText(item.getVideoTitle());
            if(item.getPublishType().equals("1")){
                mVideoTypeIV.setVisibility(View.VISIBLE);
            }else{
                mVideoTypeIV.setVisibility(View.GONE);
            }
            mViewNumTV.setText(item.getVideoBehave().getViewNum()+"");
            mLikeNumTV.setText(item.getVideoBehave().getLikeNum()+"");
            mCommentNumTV.setText(item.getVideoBehave().getCommentNum()+"");
            if (item.getVideoAuthor().getAvatar() != null && !item.getVideoAuthor().getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getVideoAuthor().getAvatar()).into(mAuthorAvatarTV);
            }
            mAuthorNicknameTV.setText(item.getVideoAuthor().getNickName());
        }
    }
}