package com.roydon.niuyin.ui.fragment.index;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.roydon.niuyin.widget.TouchViewPager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

/**
 * desc   : 首页视频流
 */
public final class IndexVideoFeedFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {

    @BindView(R.id.viewPager)
    TouchViewPager viewPager;

    ArrayList<Fragment> fragments = new ArrayList<>();

    public static IndexVideoFeedFragment newInstance() {
        return new IndexVideoFeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_video_feed;
    }

    @Override
    protected void initView() {
//        fragments.add(IndexVideoFeedRecommendFragment.newInstance());
        fragments.add(IndexVideoFeedUserFragment.newInstance());
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(new HomeAdapter(getChildFragmentManager(), new String[]{}, fragments));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 如果不可见 则 fragments 切换为第一个
        if (!isVisibleToUser && Objects.nonNull(getView())) {
            viewPager.setCurrentItem(0);
        }
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