package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.search.HotVideoSearchApi;
import com.roydon.niuyin.http.request.search.VideoSearchHistoryApi;
import com.roydon.niuyin.http.response.search.VideoSearchHistory;
import com.roydon.niuyin.ui.adapter.HotVideoSearchAdapter;
import com.roydon.niuyin.ui.adapter.VideoSearchHistoryAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class VideoSearchActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_SEARCH_HISTORY = 1;
    private static final int HANDLER_VIDEO_SEARCH_HISTORY_EMPTY = 2;
    private static final int HANDLER_VIDEO_SEARCH_HISTORY_ERROR = 3;
    private static final int HANDLER_HOT_VIDEO_SEARCH = 10;
    private static final int HANDLER_HOT_VIDEO_SEARCH_EMPTY = 11;
    private static final int HANDLER_HOT_VIDEO_SEARCH_ERROR = 12;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_hint_keyword)
    EditText mHintKeyword;
    @BindView(R.id.tv_search)
    TextView mSearch;
    // 搜索记录
    @BindView(R.id.ll_search_history)
    LinearLayout mSearchHistoryLayout;
    @BindView(R.id.videoSearchHistoryRecyclerView)
    RecyclerView mVideoSearchHistoryRecyclerView;
    private VideoSearchHistoryAdapter mVideoSearchHistoryAdapter;
    private List<VideoSearchHistory> mVideoSearchHistoryList;
    // 热搜
    @BindView(R.id.ll_hot_search)
    LinearLayout mHotSearchLayout;
    @BindView(R.id.hotSearchRecyclerView)
    RecyclerView mHotSearchRecyclerView;
    private HotVideoSearchAdapter mHotVideoSearchAdapter;
    private String[] mHotVideoSearchs;
    private int hotVideoSearchPageNum = 1;
    private int hotVideoSearchPageSize = 50;

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
                VideoSearchResultActivity.start(this, mHintKeyword.getText().toString());
            }
        });
        // 历史搜索
        mSearchHistoryLayout.setVisibility(View.GONE);
        mVideoSearchHistoryAdapter = new VideoSearchHistoryAdapter(this);
        mVideoSearchHistoryAdapter.setOnItemClickListener(this);
        mVideoSearchHistoryRecyclerView.setAdapter(mVideoSearchHistoryAdapter);
        // 芝士热搜
        mHotSearchLayout.setVisibility(View.GONE);
        mHotVideoSearchAdapter = new HotVideoSearchAdapter(this);
        mHotVideoSearchAdapter.setOnItemClickListener(this);
        mHotSearchRecyclerView.setAdapter(mHotVideoSearchAdapter);

    }

    @Override
    protected void initData() {
        getVideoSearchHistory();
        getHotVideoSearch();
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
                            return;
                        }
                        mVideoSearchHistoryList = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_SEARCH_HISTORY);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_SEARCH_HISTORY_ERROR);
                    }
                });
    }

    /**
     * 芝士热搜
     */
    public void getHotVideoSearch() {
        EasyHttp.post(this)
                .api(new HotVideoSearchApi().setPageNum(hotVideoSearchPageNum).setPageSize(hotVideoSearchPageSize))
                .request(new HttpCallback<HttpData<String[]>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<String[]> data) {
                        if (Objects.isNull(data) || data.getData().length == 0) {
                            mHandler.sendEmptyMessage(HANDLER_HOT_VIDEO_SEARCH_EMPTY);
                            return;
                        }
                        mHotVideoSearchs = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_HOT_VIDEO_SEARCH);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mHandler.sendEmptyMessage(HANDLER_HOT_VIDEO_SEARCH_ERROR);
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
                case HANDLER_VIDEO_SEARCH_HISTORY_EMPTY:
                    mSearchHistoryLayout.setVisibility(View.GONE);
                    break;
                case HANDLER_VIDEO_SEARCH_HISTORY:
                    mSearchHistoryLayout.setVisibility(View.VISIBLE);
                    mVideoSearchHistoryAdapter.setData(mVideoSearchHistoryList);
                    break;
                case HANDLER_VIDEO_SEARCH_HISTORY_ERROR:
                    mSearchHistoryLayout.setVisibility(View.GONE);
                    break;
                case HANDLER_HOT_VIDEO_SEARCH_EMPTY:
                    mHotSearchLayout.setVisibility(View.GONE);
                    break;
                case HANDLER_HOT_VIDEO_SEARCH:
                    mHotSearchLayout.setVisibility(View.VISIBLE);
                    mHotVideoSearchAdapter.setData(Arrays.asList(mHotVideoSearchs));
                    break;
                case HANDLER_HOT_VIDEO_SEARCH_ERROR:
                    mHotSearchLayout.setVisibility(View.GONE);
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
        if (recyclerView == mVideoSearchHistoryRecyclerView) {
            // 点击事件发生在 mVideoSearchHistoryRecyclerView 上
            VideoSearchHistory item = mVideoSearchHistoryAdapter.getItem(position);
            if (item == null) return;
            VideoSearchResultActivity.start(this, item.getKeyword());
        } else if (recyclerView == mHotSearchRecyclerView) {
            // 点击事件发生在 mHotSearchRecyclerView 上
            String item = mHotVideoSearchAdapter.getItem(position);
            if (item == null) return;
            VideoSearchResultActivity.start(this, item);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}