package com.roydon.niuyin.ui.fragment.index;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * desc   : 首页视频流
 */
public final class IndexVideoFeedUserFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {


    public static IndexVideoFeedUserFragment newInstance() {
        return new IndexVideoFeedUserFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_video_feed_user;
    }

    @Override
    protected void initView() {


    }

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