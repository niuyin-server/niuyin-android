package com.roydon.niuyin.ui.fragment.followfans;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.social.FollowPageApi;
import com.roydon.niuyin.http.request.social.FollowUserApi;
import com.roydon.niuyin.http.request.video.RecommendVideoApi;
import com.roydon.niuyin.http.response.social.FollowUser;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.ui.activity.FollowFansActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.FollowUserAdapter;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public final class FollowFragment extends MyFragment<FollowFansActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_FOLLOW_PAGE = 10;
    private static final int HANDLER_FOLLOW_PAGE_MORE = 11;
    private static final int HANDLER_FOLLOW_PAGE_ERROR = 12;

    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    WrapRecyclerView mRecyclerView;

    private FollowUserAdapter mAdapter;
    private List<FollowUser> followUserList;

    private int pageNum = 1;
    private int pageSize = 10;

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    /**
     * 获取提示布局
     */
    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView() {
        showLoading();
        mAdapter = new FollowUserAdapter(getContext());
        mAdapter.setOnItemChildClickListener(new FollowUserAdapter.OnItemChildClickListener() {
            @Override
            public void onCancelFollow(View view, int position, FollowUser item) {
                toast(item.getUserId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void lazyLoadData() {
        apiFollowPage(true);
    }

    @Override
    protected void initData() {

    }

    /**
     * 当 RecyclerView 某个条目被点击时回调
     *
     * @param recyclerView RecyclerView 对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

    }

    /**
     * 当 RecyclerView 某个条目被长按时回调
     *
     * @param recyclerView RecyclerView 对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     * @return 是否拦截事件
     */
    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
        return false;
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
                case HANDLER_FOLLOW_PAGE:
                    mAdapter.setData(followUserList);
                    showComplete();
                    break;
                case HANDLER_FOLLOW_PAGE_MORE:
                    mAdapter.setMoreData(followUserList);
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        apiFollowPage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        apiFollowPage(true);
    }

    /**
     * 分页我的关注列表
     *
     * @param isRefresh
     */
    private void apiFollowPage(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new FollowPageApi().setPageNum(pageNum).setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<FollowUser>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<FollowUser> rows) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            followUserList = rows.getRows();
                            // 更新ui
                            mHandler.sendEmptyMessage(HANDLER_FOLLOW_PAGE);
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            followUserList = rows.getRows();
                            mHandler.sendEmptyMessage(HANDLER_FOLLOW_PAGE_MORE);
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }
}