package com.roydon.niuyin.ui.fragment.index;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseAdapter;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.helper.SPManager;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoCategoryActivity;
import com.roydon.niuyin.ui.activity.VideoSearchActivity;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.roydon.niuyin.widget.XCollapsingToolbarLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * desc   : 首页视频流
 */
public final class IndexVideoFeedFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {


    public static IndexVideoFeedFragment newInstance() {
        return new IndexVideoFeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_video_feed;
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