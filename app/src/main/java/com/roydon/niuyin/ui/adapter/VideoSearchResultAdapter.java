package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
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
import com.roydon.niuyin.http.response.search.AppVideoSearchVO;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/3 23:18
 * @description 视频搜索适配器
 */
public class VideoSearchResultAdapter extends MyAdapter<AppVideoSearchVO> {

    public VideoSearchResultAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public VideoSearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoSearchResultAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_search_result);
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
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeView;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            AppVideoSearchVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverView);
            }
            CharSequence styledText = Html.fromHtml(item.getVideoTitle(), Html.FROM_HTML_MODE_LEGACY);
            mTitleView.setText(styledText);
            if (item.getAuthor().getAvatar() != null && !item.getAuthor().getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAuthor().getAvatar()).into(mAvatarView);
            }
            mNicknameView.setText(item.getAuthor().getNickName());
            if ((PublishType.VIDEO.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.GONE);
            } else if ((PublishType.IMAGE.getCode()).equals(item.getPublishType())) {
                mVideoTypeView.setVisibility(View.VISIBLE);
            }
            mViewNumView.setText(item.getViewNum().toString());
            mPublishTimeView.setText(TimeUtils.getSmartDate(DateUtils.localDateTime2Long(item.getCreateTime())));
        }
    }
}