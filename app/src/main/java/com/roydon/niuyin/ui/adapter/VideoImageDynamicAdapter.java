package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.social.DynamicUser;
import com.roydon.niuyin.http.response.video.VideoVO;
import com.roydon.niuyin.ui.activity.ImageActivity;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;
import com.youth.banner.Banner;
import com.youth.banner.holder.BannerImageHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 视频、图文动态
 */
public final class VideoImageDynamicAdapter extends MyAdapter<VideoVO> {

    public VideoImageDynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        String publishType = getItem(position).getPublishType();
        return Integer.parseInt(publishType);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolderVideo();
        } else if (viewType == 1) {
            return new ViewHolderImage();
        } else {
            return null;
        }
    }

    final class ViewHolderVideo extends MyAdapter.ViewHolder {

        ViewHolderVideo() {
            super(R.layout.item_video_dynamic);
        }

        @BindView(R.id.iv_author_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_author_nickname)
        TextView mNicknameTV;
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeTV;
        @BindView(R.id.iv_cover)
        ImageView mCoverIV;
        @BindView(R.id.tv_title)
        TextView mTitleIV;
        @BindView(R.id.tv_like_num)
        TextView mLikeNumIV;
        @BindView(R.id.tv_comment_num)
        TextView mCommentNumIV;
        @BindView(R.id.tv_favorite_num)
        TextView mFavoriteNumIV;

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindView(int position) {
            VideoVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getUserAvatar() != null && !item.getUserAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getUserAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getUserNickName());
            mPublishTimeTV.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(item.getCreateTime())));
            if (item.getCoverImage() != null && !item.getCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getCoverImage()).into(mCoverIV);
            }
            mTitleIV.setText(item.getVideoTitle());
            mLikeNumIV.setText(item.getLikeNum() + "");
            mCommentNumIV.setText(item.getLikeNum() + "");
            mFavoriteNumIV.setText(item.getLikeNum() + "");
        }
    }

    final class ViewHolderImage extends MyAdapter.ViewHolder {

        ViewHolderImage() {
            super(R.layout.item_image_dynamic);
        }

        @BindView(R.id.iv_author_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_author_nickname)
        TextView mNicknameTV;
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeTV;
        @BindView(R.id.bannerViewPager)
        BannerViewPager bannerViewPager;
        @BindView(R.id.tv_title)
        TextView mTitleIV;
        @BindView(R.id.tv_like_num)
        TextView mLikeNumIV;
        @BindView(R.id.tv_comment_num)
        TextView mCommentNumIV;
        @BindView(R.id.tv_favorite_num)
        TextView mFavoriteNumIV;

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindView(int position) {
            VideoVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getUserAvatar() != null && !item.getUserAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getUserAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getUserNickName());
            mPublishTimeTV.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(item.getCreateTime())));
            List<String> imgs = Arrays.asList(item.getImageList());
            int size = imgs.size();

            int widthPixels = getResources().getDisplayMetrics().widthPixels;
            int ceil = (widthPixels - 40 * size) / size;
            bannerViewPager.setAdapter(new BannerAdapter())
                    .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                    .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                    .setIndicatorGravity(IndicatorGravity.CENTER)
                    .setIndicatorSliderWidth(ceil)
                    .setIndicatorHeight(10)
                    .setIndicatorSliderColor(getResources().getColor(R.color.gray50), getResources().getColor(R.color.white))
                    .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                        @Override
                        public void onPageClick(View clickedView, int position) {
                            ArrayList<String> arrayList = new ArrayList<>(imgs);
                            ImageActivity.start(getContext(), arrayList, position);
                        }
                    })
                    .create(imgs);

            mTitleIV.setText(item.getVideoTitle());
            mLikeNumIV.setText(item.getLikeNum() + "");
            mCommentNumIV.setText(item.getLikeNum() + "");
            mFavoriteNumIV.setText(item.getLikeNum() + "");
        }
    }
}