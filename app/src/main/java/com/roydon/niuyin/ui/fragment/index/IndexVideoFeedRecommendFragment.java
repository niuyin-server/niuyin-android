package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.RxBus;
import com.roydon.niuyin.helper.player.PauseVideoEvent;
import com.roydon.niuyin.helper.player.VideoPlayer;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.VideoAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Objects;

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
    private static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

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
        initVideoPlayer();
        setViewPagerLayoutManager();
        setRefreshEvent();
        observeEvent();
    }

    private void initRecyclerView() {
        adapter = new VideoAdapter(requireContext(), (RecyclerView) viewPager2.getChildAt(0));
        viewPager2.setAdapter(adapter);
        getRecommendVideoList();
    }

    private void initVideoPlayer() {
        // 确保 requireActivity() 不为空
        Activity activity = requireActivity();
        if (Objects.isNull(activity)) {
            throw new IllegalStateException("Activity cannot be null");
        }
        // 初始化 videoView 并设置布局参数
        videoView = new VideoPlayer(activity, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        videoView.setLayoutParams(params);
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
//            playCurVideo(position);
        }
    };

    private void setRefreshEvent() {
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            refreshLayout.finishRefresh();
                        }
                    }.start();
                }
            });
        } else {
        }
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

    }
}