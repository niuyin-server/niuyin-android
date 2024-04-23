package com.roydon.niuyin.ui.fragment.videoplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengShare;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.behave.VideoCommentParentPageApi;
import com.roydon.niuyin.http.request.behave.VideoLikeApi;
import com.roydon.niuyin.http.request.behave.VideoUnlikeApi;
import com.roydon.niuyin.http.request.notice.UnreadNoticeCountApi;
import com.roydon.niuyin.http.request.social.FollowUserApi;
import com.roydon.niuyin.http.request.video.RelateVideoRecommendApi;
import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.http.response.video.RelateVideoVO;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.other.MediaVideoInfo;
import com.roydon.niuyin.ui.activity.UserProfileActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.VideoCommentParentAdapter;
import com.roydon.niuyin.ui.adapter.VideoRelateRecommendAdapter;
import com.roydon.niuyin.ui.adapter.VideoTagAdapter;
import com.roydon.niuyin.ui.dialog.MessageDialog;
import com.roydon.niuyin.ui.dialog.ShareDialog;
import com.roydon.niuyin.ui.dialog.VideoCommentReplayDialog;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;
import com.roydon.niuyin.utils.VideoPlayUtil;
import com.roydon.niuyin.widget.HintLayout;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description niuyin-android
 */
public class VideoInfoFragment extends MyFragment<VideoPlayActivity> implements StatusAction, OnRefreshLoadMoreListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_RELATE_RECOMMEND = 1;
    private static final int HANDLER_VIDEO_RELATE_RECOMMEND_MORE = 2;
    private static final int HANDLER_VIDEO_LIKE_SUCCESS = 10;
    private static final int HANDLER_VIDEO_UNLIKE_SUCCESS = 11;

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
    @BindView(R.id.btn_follow_user)
    Button followUserBTN;
    //    behave 操作
    @BindView(R.id.sb_like)
    ShineButton mBehaveLikeButton;
    @BindView(R.id.sb_not_like)
    ShineButton mBehaveNotLikeButton;
    @BindView(R.id.sb_favorite)
    ShineButton mBehaveFavoriteButton;
    @BindView(R.id.sb_share)
    ImageView mBehaveShareButton;
    @BindView(R.id.tv_like_num)
    TextView videoLikeNumTV;
    @BindView(R.id.tv_favorite_num)
    TextView videoFavoriteNumTV;

    @BindView(R.id.videoTagRecyclerView)
    RecyclerView mVideoTagRecyclerView;
    private VideoTagAdapter mVideoTagAdapter;

    private VideoInfoVO videoInfoVO;

    // 相关推荐
    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    WrapRecyclerView mRecyclerView;

    VideoRelateRecommendAdapter videoRelateRecommendAdapter;
    private List<RelateVideoVO> relateVideoVOList;

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
        mVideoTagAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                // 视频标签点击事件
                String videoTag = mVideoTagAdapter.getItem(position);
                toast(videoTag);
            }
        });
        mVideoTagRecyclerView.setAdapter(mVideoTagAdapter);
        setOnClickListener(R.id.ll_author, R.id.sb_like, R.id.sb_not_like, R.id.sb_favorite, R.id.sb_share, R.id.ll_open_desc, R.id.btn_follow_user);
        mBehaveLikeButton.init(getAttachActivity());
        mBehaveNotLikeButton.init(getAttachActivity());
        mBehaveFavoriteButton.init(getAttachActivity());
        mBehaveLikeButton.setOnClickListener(this);
        mBehaveNotLikeButton.setOnClickListener(this);
        mBehaveFavoriteButton.setOnClickListener(this);
        mBehaveShareButton.setOnClickListener(this);
        // 视频相关推荐
        showLoading();
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        // 禁用刷新
        mRefreshLayout.setEnableRefresh(false);
        videoRelateRecommendAdapter = new VideoRelateRecommendAdapter(getContext());
        videoRelateRecommendAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                RelateVideoVO item = videoRelateRecommendAdapter.getItem(position);
//                toast("点击了视频 " + item.getPublishType());
//                getAttachActivity().reLoadNewVideo(item.getVideoId());
                if (item.getPublishType().equals(PublishType.VIDEO.getCode())) {
                    // 视频
                    // 设置视频比例 4:3竖屏视频
                    if (!Objects.isNull(item.getVideoInfo())) {
                        MediaVideoInfo mediaVideoInfo = new Gson().fromJson(item.getVideoInfo(), MediaVideoInfo.class);
                        if (mediaVideoInfo.getWidth() > mediaVideoInfo.getHeight()) {
                            // 横屏 1.6
                            VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.HENG.getCode());
                        } else if (mediaVideoInfo.getHeight() > mediaVideoInfo.getWidth()) {
                            // 竖屏 0.75
                            VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.SHU.getCode());
                        } else if (Objects.equals(mediaVideoInfo.getWidth(), mediaVideoInfo.getHeight())) {
                            // 正方形视频
                            VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.SQUARE.getCode());
                        } else {
                            VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.DEFAULT.getCode());
                        }
                    } else {
                        VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.DEFAULT.getCode());
                    }
                } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
                    // 图文
                    VideoImagePlayActivity.start(getContext(), item.getVideoId());
                }
            }
        });
        videoRelateRecommendAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
                return false;
            }
        });
        mRecyclerView.setAdapter(videoRelateRecommendAdapter);
    }

    @Override
    protected void lazyLoadData() {
        getVideoRelateRecommend(true);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initData() {
        if (videoInfoVO.getAuthor() != null && !videoInfoVO.getAuthor().getAvatar().equals("")) {
            GlideApp.with(this).load(videoInfoVO.getAuthor().getAvatar()).circleCrop().into(mAuthorAvatarView);
        }
        mAuthorNicknameView.setText(videoInfoVO.getAuthor().getNickName());
        mVideoTitleView.setText(videoInfoVO.getVideoTitle());
        mViewNumView.setText(videoInfoVO.getViewNum().toString());
        mPublishTimeView.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(videoInfoVO.getCreateTime())));
        mVideoDescView.setText(videoInfoVO.getVideoDesc() == null || videoInfoVO.getVideoDesc().equals("") ? "-" : videoInfoVO.getVideoDesc());
        mVideoTagAdapter.setData(Arrays.asList(videoInfoVO.getTags()));
        videoLikeNumTV.setText(videoInfoVO.getLikeNum() != null ? videoInfoVO.getLikeNum().toString() : "0");
        videoFavoriteNumTV.setText(videoInfoVO.getFavoriteNum() != null ? videoInfoVO.getFavoriteNum().toString() : "0");
        mBehaveLikeButton.setChecked(videoInfoVO.isWeatherLike());
        mBehaveFavoriteButton.setChecked(videoInfoVO.isWeatherFavorite());
        mBehaveFavoriteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MessageDialog.Builder(getContext())
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容必须要填写
                        .setMessage("我是内容")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                return false;
            }
        });
        if (videoInfoVO.isWeatherFollow()) {
            followUserBTN.setEnabled(false);
            followUserBTN.setText("已关注");
        } else {
            followUserBTN.setEnabled(true);
            followUserBTN.setText("关注");
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getVideoRelateRecommend(false);
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
//                toast("like" + mBehaveLikeButton.isChecked());
                if (mBehaveLikeButton.isChecked()) {
                    apiVideoLike(videoInfoVO.getVideoId());
                } else {
                    apiVideoUnlike(videoInfoVO.getVideoId());
                }
                break;
            case R.id.sb_not_like:
                break;
            case R.id.sb_favorite:
                break;
            case R.id.sb_share:
                new ShareDialog.Builder(getContext())
                        // 分享标题
                        .setShareTitle(videoInfoVO.getVideoTitle())
                        // 分享描述
                        .setShareDescription(videoInfoVO.getVideoDesc())
                        // 分享缩略图
                        .setShareLogo(videoInfoVO.getCoverImage())
                        // 分享链接
                        .setShareUrl(videoInfoVO.getVideoUrl())
                        .setListener(new UmengShare.OnShareListener() {

                            @Override
                            public void onSucceed(Platform platform) {
                                toast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, Throwable t) {
                                toast("分享出错");
                            }

                            @Override
                            public void onCancel(Platform platform) {
                                toast("分享取消");
                            }
                        })
                        .show();
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
            case R.id.btn_follow_user:
                apiFollowUser(videoInfoVO.getUserId());
                followUserBTN.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_VIDEO_RELATE_RECOMMEND:
                    videoRelateRecommendAdapter.setData(relateVideoVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_RELATE_RECOMMEND_MORE:
                    videoRelateRecommendAdapter.setMoreData(relateVideoVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_LIKE_SUCCESS:
                    toast("点赞成功");
                    videoLikeNumTV.setText((Integer.parseInt(videoLikeNumTV.getText().toString()) + 1) + "");
                    break;
                case HANDLER_VIDEO_UNLIKE_SUCCESS:
                    videoLikeNumTV.setText((Integer.parseInt(videoLikeNumTV.getText().toString()) - 1) + "");
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 获取相关视频
     *
     * @param isRefresh
     */
    private void getVideoRelateRecommend(boolean isRefresh) {
        EasyHttp.get(this).api(new RelateVideoRecommendApi().setVideoId(videoInfoVO.getVideoId()))
                .request(new HttpCallback<HttpData<List<RelateVideoVO>>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<RelateVideoVO>> data) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            relateVideoVOList = data.getData();
                            // 更新ui
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_RELATE_RECOMMEND);
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            relateVideoVOList = data.getData();
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_RELATE_RECOMMEND_MORE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });

    }

    /**
     * 关注用户
     */
    private void apiFollowUser(Long userId) {
        EasyHttp.get(this).api(new FollowUserApi().setUserId(userId))
                .request(new HttpCallback<HttpData<Boolean>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        Boolean data1 = data.getData();
                        if (data1) {
                            toast("关注成功");
                        } else {
                            toast(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    /**
     * 视频点赞
     */
    private void apiVideoLike(String videoId) {
        EasyHttp.get(this)
                .api(new VideoLikeApi().setVideoId(videoId))
                .request(new HttpCallback<HttpData<Boolean>>(getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        if (data.getData()) {
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_LIKE_SUCCESS);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 视频取消点赞
     */
    private void apiVideoUnlike(String videoId) {
        EasyHttp.get(this)
                .api(new VideoUnlikeApi().setVideoId(videoId))
                .request(new HttpCallback<HttpData<Boolean>>(getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        if (data.getData()) {
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_UNLIKE_SUCCESS);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
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