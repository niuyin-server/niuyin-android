package com.roydon.niuyin.ui.fragment.index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.MemberInfoVO;
import com.roydon.niuyin.http.response.VideoRecommendVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.ui.adapter.StatusAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 14:13
 * @description 首页推荐
 */
public final class IndexRecommendFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_RECOMMEND_VIDEO = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    RecyclerView mRecyclerView;

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
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        getRecommendVideoList(true);
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
                    break;
                case HANDLER_RECOMMEND_VIDEO:
                    mAdapter.setData(videoRecommendVOList);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(mAdapter.getItem(position).getVideoId());
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