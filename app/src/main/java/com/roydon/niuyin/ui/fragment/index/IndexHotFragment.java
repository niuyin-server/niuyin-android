package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
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
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.video.HotVideoApi;
import com.roydon.niuyin.http.response.video.VideoVO;
import com.roydon.niuyin.other.MediaVideoInfo;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.HotVideoAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/1 19:33
 * @description 热门视频
 */
public final class IndexHotFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener ,BaseAdapter.OnItemLongClickListener{

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_HOT_VIDEO = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    private HotVideoAdapter mAdapter;

    private List<VideoVO> videoVOList;

    private int pageNum = 1;
    private int pageSize = 10;

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
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
    }

    @Override
    protected void lazyLoadData() {
        getHotVideoPage(true);
    }

    @Override
    protected void initData() {

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
                        .setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<VideoVO>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<VideoVO> rows) {
                        if (rows.getTotal() == 0) {
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                            return;
                        }
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            videoVOList = rows.getRows();
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            videoVOList.addAll(rows.getRows());
                            if (Objects.isNull(rows.getRows()) || rows.getRows().isEmpty() || rows.getRows().size() < pageSize) {
                                mRefreshLayout.setNoMoreData(true);
                                TextView footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                                footerView.setText("— 我也是有底线的 —");
                                footerView.setOnClickListener(v -> toast("点击了尾部"));
                            }
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
        VideoVO item = mAdapter.getItem(position);
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
                    VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.SHU.getCode());
                }else if(Objects.equals(mediaVideoInfo.getWidth(), mediaVideoInfo.getHeight())){
                    // 正方形视频
                    VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.SQUARE.getCode());
                }
            }else {
                VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.DEFAULT.getCode());
            }
//            VideoPlayActivity.start(getContext(), videoVO.getVideoId(), VideoScreenType.DEFAULT.getCode());
        } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(getContext(), item.getVideoId());
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
        return true;
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