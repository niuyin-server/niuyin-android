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
import com.roydon.niuyin.http.response.VideoVO;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;

import java.time.ZoneOffset;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 20:11
 * @description niuyin-android
 */
public class HotVideoAdapter extends MyAdapter<VideoVO> {

    public HotVideoAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HotVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotVideoAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_hot_video);
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
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            VideoVO item = getItem(position);
            if (item.getCoverImage() != null || !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            mTitleView.setText(item.getVideoTitle());
            if (item.getUserAvatar() != null || !item.getUserAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getUserAvatar()).into(mAvatarView);
            }
            mNicknameView.setText(item.getUserNickName());
            if (item.getPublishType().equals(PublishType.VIDEO.getCode())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            mViewNumView.setText(item.getViewNum().toString());
            mLikeNumView.setText(item.getLikeNum().toString());
            mPublishTimeView.setText(TimeUtils.getSmartDate(DateUtils.localDateTime2Long(item.getCreateTime())));
        }
    }
}