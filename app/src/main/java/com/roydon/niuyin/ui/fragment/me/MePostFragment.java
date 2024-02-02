package com.roydon.niuyin.ui.fragment.me;

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
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.behave.MyPostPageApi;
import com.roydon.niuyin.http.response.video.MyVideoVO;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.MePostAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 12:00
 * @description niuyin-android
 */
@SuppressLint("NonConstantResourceId")
public class MePostFragment extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_MY_PAGE = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    private MePostAdapter mAdapter;

    private List<MyVideoVO> myVideoVOList;

    private int pageNum = 1;
    private int pageSize = 15;

    public static MePostFragment newInstance() {
        return new MePostFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_post;
    }

    @Override
    protected void initView() {
        mAdapter = new MePostAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
    }

    @Override
    protected void initData() {
        getMyPostPage(true);
    }

    /**
     * 分页获取我的作品
     *
     * @param isRefresh
     */
    public void getMyPostPage(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new MyPostPageApi()
                        .setPageNum(pageNum)
                        .setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<MyVideoVO>>(this.getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<MyVideoVO> rows) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            myVideoVOList = rows.getRows();
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            myVideoVOList.addAll(rows.getRows() == null ? new ArrayList<>() : rows.getRows());
                        }
                        if (rows.getRows().size() < myVideoVOList.size()) {
                            mRefreshLayout.setEnableLoadMore(false);
                            toast("not have more");
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_MY_PAGE);
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast("加载失败");
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
                case HANDLER_MY_PAGE:
                    mAdapter.setData(myVideoVOList);
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
        MyVideoVO myVideoVO = mAdapter.getItem(position);
        if (myVideoVO.getPublishType().equals(PublishType.VIDEO.getCode())) {
            // 视频
            VideoPlayActivity.start(getContext(), myVideoVO.getVideoId());
        } else if (myVideoVO.getPublishType().equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(getContext(), myVideoVO.getVideoId());
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getMyPostPage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        mRefreshLayout.setEnableLoadMore(true);
        getMyPostPage(true);
    }
}