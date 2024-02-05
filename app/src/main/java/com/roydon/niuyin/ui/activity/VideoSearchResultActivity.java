package com.roydon.niuyin.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.search.VideoSearchApi;
import com.roydon.niuyin.http.response.search.AppVideoSearchVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.VideoSearchResultAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class VideoSearchResultActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_SEARCH = 1;
    private static final int HANDLER_VIDEO_SEARCH_EMPTY = 2;

    @DebugLog
    public static void start(Context context, String keyword) {
        Intent intent = new Intent(context, VideoSearchResultActivity.class);
        intent.putExtra(IntentKey.SEARCH_KEYWORD, keyword);
        context.startActivity(intent);
    }

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_hint_keyword)
    EditText mHintKeyword;
    @BindView(R.id.tv_search)
    TextView mSearchView;
    // 搜索
    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;
    private VideoSearchResultAdapter mVideoSearchResultAdapter;
    private List<AppVideoSearchVO> mAppVideoSearchVOList;
    // 搜索请求参数
    private String keyword;
    private int pageNum = 1;
    private int pageSize = 10;
    private String publishTimeLimit = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_search_result;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.iv_back, R.id.tv_search);
        mVideoSearchResultAdapter = new VideoSearchResultAdapter(this);
        mVideoSearchResultAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mVideoSearchResultAdapter);
        showLoading();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                if (!TextUtils.isEmpty(mHintKeyword.getText().toString())) {
//                    VideoSearchResultActivity.start(this, mHintKeyword.getText().toString());
                    pageNum = 1;
                    pageSize = 10;
                    publishTimeLimit = "0";
                    postVideoSearch(true);
                    showLoading();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        mHintKeyword.setText(getString(IntentKey.SEARCH_KEYWORD));
        postVideoSearch(true);
    }

    /**
     * 视频搜索
     */
    private void postVideoSearch(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new VideoSearchApi()
                        .setPageNum(pageNum)
                        .setPageSize(pageSize)
                        .setKeyword(mHintKeyword.getText().toString())
                        .setPublishTimeLimit(publishTimeLimit))
                .request(new HttpCallback<PageDataInfo<AppVideoSearchVO>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<AppVideoSearchVO> rows) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            mAppVideoSearchVOList = rows.getRows();
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            mAppVideoSearchVOList.addAll(rows.getRows());
                            if (Objects.isNull(rows.getRows()) || rows.getRows().isEmpty() || rows.getRows().size() < pageSize) {
                                mRefreshLayout.setNoMoreData(true);
                                TextView footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                                footerView.setText("— 我也是有底线的 —");
                                footerView.setOnClickListener(v -> toast("点击了尾部"));
                            }
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_SEARCH);
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
                case HANDLER_VIDEO_SEARCH:
                    mVideoSearchResultAdapter.setData(mAppVideoSearchVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_SEARCH_EMPTY:
                    showEmpty();
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
        AppVideoSearchVO item = mVideoSearchResultAdapter.getItem(position);
        if (item.getPublishType().equals(PublishType.VIDEO.getCode())) {
            // 视频
            VideoPlayActivity.start(this, item.getVideoId(), VideoScreenType.DEFAULT.getCode());
        } else if (item.getPublishType().equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(this, item.getVideoId());
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        toast("上拉加载");
        pageNum++;
        postVideoSearch(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        toast("下拉刷新");
        pageNum = 1;
        mRefreshLayout.setEnableLoadMore(true);
        postVideoSearch(false);
    }
}