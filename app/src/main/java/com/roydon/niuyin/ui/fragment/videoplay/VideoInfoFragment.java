package com.roydon.niuyin.ui.fragment.videoplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.hjq.base.BaseAdapter;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.ui.activity.UserProfileActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.VideoTagAdapter;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;
import com.roydon.niuyin.widget.HintLayout;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description niuyin-android
 */
public class VideoInfoFragment extends MyFragment<VideoPlayActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    @BindView(R.id.ll_author)
    LinearLayout mAuthorLayout;
    @BindView(R.id.iv_author_avatar)
    ImageView mAuthorAvatarView;
    @BindView(R.id.tv_author_nickname)
    TextView mAuthorNicknameView;
    // 视频详情
    @BindView(R.id.tv_video_title)
    TextView mVideoTitleView;
    @BindView(R.id.tv_view_num)
    TextView mViewNumView;
    @BindView(R.id.mc_video_desc)
    MaterialCardView mVideoDescMaterialCardView;
    @BindView(R.id.tv_video_desc)
    TextView mVideoDescView;
    @BindView(R.id.tv_publish_time)
    TextView mPublishTimeView;
    @BindView(R.id.ll_open_desc)
    LinearLayout mOpenDescLayout;
    @BindView(R.id.iv_open_desc)
    ImageView mOpenDescView;
    //    behave 操作
    @BindView(R.id.sb_like)
    ShineButton mBehaveLikeButton;
    @BindView(R.id.sb_not_like)
    ShineButton mBehaveNotLikeButton;
    @BindView(R.id.sb_favorite)
    ShineButton mBehaveFavoriteButton;
    @BindView(R.id.sb_share)
    ShineButton mBehaveShareButton;

    @BindView(R.id.videoTagRecyclerView)
    RecyclerView mVideoTagRecyclerView;
    private VideoTagAdapter mVideoTagAdapter;

    private VideoInfoVO videoInfoVO;

    public static VideoInfoFragment newInstance() {
        return new VideoInfoFragment();
    }

    public void setVideoInfoVO(VideoInfoVO videoInfoVO) {
        this.videoInfoVO = videoInfoVO;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_info;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        mVideoDescMaterialCardView.setVisibility(View.GONE);
        //视频标签
        mVideoTagAdapter = new VideoTagAdapter(getContext());
        mVideoTagAdapter.setOnItemClickListener(this);
        mVideoTagRecyclerView.setAdapter(mVideoTagAdapter);
        setOnClickListener(R.id.ll_author, R.id.sb_like, R.id.sb_not_like, R.id.sb_favorite, R.id.sb_share, R.id.ll_open_desc);
        mBehaveLikeButton.init(getAttachActivity());
        mBehaveNotLikeButton.init(getAttachActivity());
        mBehaveFavoriteButton.init(getAttachActivity());
        mBehaveShareButton.init(getAttachActivity());
        mBehaveLikeButton.setOnClickListener(this);
        mBehaveNotLikeButton.setOnClickListener(this);
        mBehaveFavoriteButton.setOnClickListener(this);
        mBehaveShareButton.setOnClickListener(this);
    }

    @Override
    protected void lazyLoadData() {

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initData() {
        if (videoInfoVO.getAuthor() != null && !videoInfoVO.getAuthor().getAvatar().equals("")) {
            GlideApp.with(this)
                    .load(videoInfoVO.getAuthor().getAvatar())
                    .circleCrop()
                    .into(mAuthorAvatarView);
        }
        mAuthorNicknameView.setText(videoInfoVO.getAuthor().getNickName());
        mVideoTitleView.setText(videoInfoVO.getVideoTitle());
        mViewNumView.setText(videoInfoVO.getViewNum().toString());
        mPublishTimeView.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(videoInfoVO.getCreateTime())));
        mVideoDescView.setText(videoInfoVO.getVideoDesc() == null || videoInfoVO.getVideoDesc().equals("") ? "-" : videoInfoVO.getVideoDesc());
        mVideoTagAdapter.setData(Arrays.asList(videoInfoVO.getTags()));
    }

    @Override
    public HintLayout getHintLayout() {
        return null;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        // 视频标签点击事件
        String videoTag = mVideoTagAdapter.getItem(position);
        toast(videoTag);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_author:
                UserProfileActivity.start(getContext(), videoInfoVO.getUserId());
                break;
            case R.id.sb_like:
                toast("like" + mBehaveLikeButton.isChecked());
                break;
            case R.id.sb_not_like:
                break;
            case R.id.sb_favorite:
                break;
            case R.id.sb_share:
                break;
            case R.id.ll_open_desc:
                if (mVideoDescMaterialCardView.getVisibility() == View.GONE) {
                    mOpenDescView.setImageResource(R.drawable.ic_up_b);
                    showCollapseCard();
                } else {
                    mOpenDescView.setImageResource(R.drawable.ic_down_b);
                    hideCollapseCard();
                }
                break;
            default:
                break;
        }
    }


    private void showCollapseCard() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        mVideoDescMaterialCardView.startAnimation(fadeInAnimation);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mVideoDescMaterialCardView, "translationY", -mVideoDescMaterialCardView.getHeight(), 0);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        mVideoDescMaterialCardView.setVisibility(View.VISIBLE);
        animator.start();
    }

    private void hideCollapseCard() {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        mVideoDescMaterialCardView.startAnimation(fadeOutAnimation);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mVideoDescMaterialCardView, "translationY", 0, -mVideoDescMaterialCardView.getHeight());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mVideoDescMaterialCardView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }
}