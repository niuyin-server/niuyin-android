package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.other.MediaVideoInfo;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 14:13
 * @description 首页推荐
 */
public final class IndexRecommendFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_RECOMMEND_VIDEO = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    private RecommendVideoAdapter mAdapter;

    private List<VideoRecommendVO> videoRecommendVOList;

    public static IndexRecommendFragment newInstance() {
        return new IndexRecommendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_recommend;
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void initView() {
        mAdapter = new RecommendVideoAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
    }

    @Override
    protected void lazyLoadData() {
        getRecommendVideoList(true);
    }

    @Override
    protected void initData() {

    }

    /**
     * 获取推荐视频列表
     *
     * @param isRefresh
     */
    public void getRecommendVideoList(boolean isRefresh) {
        EasyHttp.get(this)
                .api(new RecommendVideoApi())
                .request(new HttpCallback<HttpData<List<VideoRecommendVO>>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<VideoRecommendVO>> data) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            videoRecommendVOList = data.getData();
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            videoRecommendVOList.addAll(data.getData());
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_RECOMMEND_VIDEO);
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast("加载失败");
                        TextView footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                        footerView.setText("我也是有底线的");
                        footerView.setOnClickListener(v -> toast("点击了尾部"));
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
                    mAdapter.setData(videoRecommendVOList);
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        VideoRecommendVO item = mAdapter.getItem(position);
        if (item.getPublishType().equals(PublishType.VIDEO.getCode())) {
            // 视频
            // 设置视频比例 4:3竖屏视频
            if (!Objects.isNull(item.getVideoInfo())) {
                MediaVideoInfo mediaVideoInfo = new Gson().fromJson(item.getVideoInfo(), MediaVideoInfo.class);
                if (mediaVideoInfo.getWidth() > mediaVideoInfo.getHeight()) {
                    // 横屏 1.6
//                    toast("横屏视频");
                    VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.HENG.getCode());
                } else if (mediaVideoInfo.getHeight() > mediaVideoInfo.getWidth()) {
                    // 竖屏 0.75
//                    toast("竖屏视频");
                    VideoPlayActivity.start(getContext(), item.getVideoId(),VideoScreenType.SHU.getCode());
                }
            }
//            VideoPlayActivity.start(getContext(), item.getVideoId());
        } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(getContext(), item.getVideoId());
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
        VideoRecommendVO item = mAdapter.getItem(position);
        toast("长按了第" + position + "个item, " + item.getVideoId());
        return true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getRecommendVideoList(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getRecommendVideoList(true);
    }
}