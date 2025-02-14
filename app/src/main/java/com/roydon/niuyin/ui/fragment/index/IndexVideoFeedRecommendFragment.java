package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.OnVideoControllerListener;
import com.roydon.niuyin.helper.RxBus;
import com.roydon.niuyin.helper.player.PauseVideoEvent;
import com.roydon.niuyin.helper.player.VideoPageChangeEvent;
import com.roydon.niuyin.helper.player.VideoPlayer;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.VideoAdapter;
import com.roydon.niuyin.widget.ControllerView;
import com.roydon.niuyin.widget.HintLayout;
import com.roydon.niuyin.widget.LikeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * desc   : 首页视频流
 */
public final class IndexVideoFeedRecommendFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {
    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_RECOMMEND_VIDEO = 1;
    private static final int HANDLER_RECOMMEND_VIDEO_MORE = 2;

    @BindView(R.id.hintLayout)
    HintLayout hintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.viewPager2)
    ViewPager2 viewPager2;

    private VideoAdapter adapter;
    /**
     * 当前播放视频位置
     */
    private int curPlayPos = -1;
    private VideoPlayer videoView;

    private ImageView ivCurCover;
    private Subscription subscribe;

    private List<VideoRecommendVO> videoRecommendVOList;

    public static IndexVideoFeedRecommendFragment newInstance() {
        return new IndexVideoFeedRecommendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_video_feed_recommend;
    }

    @Override
    protected void initView() {
        initRecyclerView();

    }

    private void initRecyclerView() {
        adapter = new VideoAdapter(requireContext(), (RecyclerView) viewPager2.getChildAt(0));
        viewPager2.setAdapter(adapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.registerOnPageChangeCallback(pageChangeCallback);
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private void initVideoPlayer() {
        // 初始化 videoView 并设置布局参数
        videoView = new VideoPlayer(requireActivity(), null);
        videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        getLifecycle().addObserver(videoView);
    }

    private void setViewPagerLayoutManager() {
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.registerOnPageChangeCallback(pageChangeCallback);
        RecyclerView recyclerViewChild = (RecyclerView) viewPager2.getChildAt(0);
//        if (recyclerViewChild != null) {
//            recyclerViewChild.scrollToPosition(PlayListActivity.initPos);
//        }
    }

    private final OnPageChangeCallback pageChangeCallback = new OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            playCurVideo(position);
        }
    };

    private void playCurVideo(int position) {
        if (position == curPlayPos) {
            return;
        }
        View itemView = adapter.getRootViewAt(position);
        ViewGroup rootView = itemView.findViewById(R.id.rl_container);
        LikeView likeView = rootView.findViewById(R.id.likeView);
        ControllerView controllerView = rootView.findViewById(R.id.controller);
        ImageView ivPlay = rootView.findViewById(R.id.iv_play);
        ImageView ivCover = rootView.findViewById(R.id.iv_cover);

        // 播放暂停事件
        likeView.setOnPlayPauseListener(new LikeView.OnPlayPauseListener() {
            @Override
            public void onPlayOrPause() {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    ivPlay.setVisibility(View.VISIBLE);
                } else {
                    videoView.play();
                    ivPlay.setVisibility(View.GONE);
                }
            }
        });

        // 评论点赞事件
        likeShareEvent(controllerView);

        // 切换播放视频的作者主页数据
        RxBus.getDefault().post(adapter.getDatas().get(position));
        curPlayPos = position;

        // 切换播放器位置
        dettachParentView(rootView);
        autoPlayVideo(curPlayPos, ivCover, controllerView);
    }

    /**
     * 移除videoview父view
     */
    private void dettachParentView(ViewGroup rootView) {
        // 1. 添加videoView到当前需要播放的item中, 添加进item之前，保证videoView没有父view
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent != null) {
            parent.removeView(videoView);
        }

        rootView.addView(videoView, 0);
    }

    /**
     * 自动播放视频
     */
    private void autoPlayVideo(int position, ImageView ivCover, ControllerView controllerView) {
        videoView.playVideo(adapter.getDatas().get(position).getMediaSource());

        videoView.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                // 播放状态发生变化时的回调
                // 播放状态包括：Player.STATE_IDLE、Player.STATE_BUFFERING、Player.STATE_READY、Player.STATE_ENDED
                if (state == Player.STATE_READY) {
                    // 可以在这里执行一些操作
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                // 播放发生错误时的回调
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                // 播放状态变为播放或暂停时的回调
            }

            @Override
            public void onRenderedFirstFrame() {
                // 第一帧已渲染，隐藏封面
                ivCover.setVisibility(View.GONE);
                ivCurCover = ivCover;
            }
        });
    }

    /**
     * 用户操作事件
     */
    private void likeShareEvent(ControllerView controllerView) {
        controllerView.setListener(new OnVideoControllerListener() {
            @Override
            public void onHeadClick() {
                RxBus.getDefault().post(new VideoPageChangeEvent(1));
            }

            @Override
            public void onLikeClick() {

            }

            @Override
            public void onCommentClick() {
//                val commentDialog = CommentDialog()
//                commentDialog.show(childFragmentManager, "")
                toast("弹窗评论");
            }

            @Override
            public void onShareClick() {
                toast("弹窗分享");
            }
        });
    }


    private void observeEvent() {
        // 监听播放或暂停事件
        subscribe = RxBus.getDefault().toObservable(PauseVideoEvent.class)
                .subscribe(new Action1<PauseVideoEvent>() {
                    @Override
                    public void call(PauseVideoEvent event) {
                        if (videoView != null) {
                            if (event.isPlayOrPause()) {
                                videoView.play();
                            } else {
                                videoView.pause();
                            }
                        } else {
                            // 处理 videoView 为 null 的情况
                        }
                    }
                });
    }

    /**
     * 获取推荐视频列表
     */
    public void getRecommendVideoList() {
        EasyHttp.get(this)
                .api(new RecommendVideoApi())
                .request(new HttpCallback<HttpData<List<VideoRecommendVO>>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<VideoRecommendVO>> data) {
                        refreshLayout.finishRefresh(true);
                        videoRecommendVOList = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_RECOMMEND_VIDEO);
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast(e.getMessage());
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_RECOMMEND_VIDEO:
                    adapter.appendList(videoRecommendVOList);
                    initVideoPlayer();
                    observeEvent();
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void lazyLoadData() {
        getRecommendVideoList();

    }

    @Override
    protected void initData() {

    }

    @Override
    public HintLayout getHintLayout() {
        return null;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
        return false;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getRecommendVideoList();
    }
}