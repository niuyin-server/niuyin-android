package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.video.HotVideoApi;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.VideoRecommendVO;
import com.roydon.niuyin.http.response.VideoVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.HotVideoAdapter;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 19:33
 * @description 热门视频
 */
public final class IndexHotFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_HOT_VIDEO = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    RecyclerView mRecyclerView;

    private HotVideoAdapter mAdapter;

    private List<VideoVO> videoVOList;

    private int pageNum = 1;

    public static IndexHotFragment newInstance() {
        return new IndexHotFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_hot;
    }

    @Override
    protected void initView() {
        mAdapter = new HotVideoAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
    }

    @Override
    protected void initData() {
        getHotVideoPage(true);
    }

    /**
     * 获取推荐视频列表
     *
     * @param isRefresh
     */
    public void getHotVideoPage(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new HotVideoApi()
                        .setPageNum(pageNum)
                        .setPageSize(10))
                .request(new HttpCallback<PageDataInfo<VideoVO>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<VideoVO> rows) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            videoVOList = rows.getRows();
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            videoVOList.addAll(rows.getRows());
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_HOT_VIDEO);
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast("加载失败");
//                        showEmpty();
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
                case HANDLER_HOT_VIDEO:
                    mAdapter.setData(videoVOList);
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        VideoVO videoVO = mAdapter.getItem(position);
        if (videoVO.getPublishType().equals(PublishType.VIDEO.getCode())) {
            // 视频
            VideoPlayActivity.start(getContext(), videoVO.getVideoId());
        } else if (videoVO.getPublishType().equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(getContext(), videoVO.getVideoId());
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getHotVideoPage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getHotVideoPage(true);
    }
}