package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.behave.MyPostPageApi;
import com.roydon.niuyin.http.request.search.VideoSearchHistoryApi;
import com.roydon.niuyin.http.response.search.VideoSearchHistory;
import com.roydon.niuyin.http.response.video.MyVideoVO;
import com.roydon.niuyin.ui.adapter.VideoSearchHistoryAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class VideoSearchActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_SEARCH_HISTORY = 1;
    private static final int HANDLER_VIDEO_SEARCH_HISTORY_EMPTY = 2;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_hint_keyword)
    EditText mHintKeyword;
    @BindView(R.id.tv_search)
    TextView mSearch;
    // 搜索记录
    @BindView(R.id.videoSearchHistoryRecyclerView)
    RecyclerView mVideoSearchHistoryRecyclerView;
    private VideoSearchHistoryAdapter mVideoSearchHistoryAdapter;
    private List<VideoSearchHistory> mVideoSearchHistoryList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_search;
    }

    @Override
    protected void initView() {
        mBack.setOnClickListener(v -> {
            finish();
        });
        // 自动获取输入框焦点
        mHintKeyword.setFocusable(true);
        mHintKeyword.setFocusableInTouchMode(true);
        mHintKeyword.requestFocus();
        // 点击搜索
        mSearch.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mHintKeyword.getText().toString())) {
                toast("点击搜索：" + mHintKeyword.getText().toString());
            }
        });
        // 历史搜索
        mVideoSearchHistoryAdapter = new VideoSearchHistoryAdapter(this);
        mVideoSearchHistoryAdapter.setOnItemClickListener(this);
        mVideoSearchHistoryRecyclerView.setAdapter(mVideoSearchHistoryAdapter);

    }

    @Override
    protected void initData() {
        getVideoSearchHistory();
    }

    /**
     * 获取我的搜素记录
     */
    public void getVideoSearchHistory() {
        EasyHttp.get(this)
                .api(new VideoSearchHistoryApi())
                .request(new HttpCallback<HttpData<List<VideoSearchHistory>>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<VideoSearchHistory>> data) {
                        if (Objects.isNull(data) || data.getData().isEmpty()) {
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_SEARCH_HISTORY_EMPTY);
                        }
                        mVideoSearchHistoryList = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_SEARCH_HISTORY);
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
                    break;
                case HANDLER_VIDEO_SEARCH_HISTORY:
                    mVideoSearchHistoryAdapter.setData(mVideoSearchHistoryList);
                    break;
                case HANDLER_VIDEO_SEARCH_HISTORY_EMPTY:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public HintLayout getHintLayout() {
        return null;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        VideoSearchHistory item = mVideoSearchHistoryAdapter.getItem(position);
        if (item == null) return;
        toast(item.getKeyword());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}