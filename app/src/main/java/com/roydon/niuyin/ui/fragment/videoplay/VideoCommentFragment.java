package com.roydon.niuyin.ui.fragment.videoplay;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.enums.VideoCommentPageOrderEnum;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.behave.CommentVideoApi;
import com.roydon.niuyin.http.request.behave.MyFavoriteVideoPageApi;
import com.roydon.niuyin.http.request.behave.VideoCommentParentPageApi;
import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.http.response.behave.MyFavoriteVideoVO;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.MeFavoriteVideoAdapter;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.ui.adapter.VideoCommentParentAdapter;
import com.roydon.niuyin.ui.dialog.VideoCommendDialog;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description 视频播放页-视频评论Fragment
 */
@SuppressLint("NonConstantResourceId")
public class VideoCommentFragment extends MyFragment<VideoPlayActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_COMMENT_PARENT_PAGE = 1;
    private static final int HANDLER_VIDEO_COMMENT_PARENT_PAGE_MORE = 2;
    private static final int HANDLER_VIDEO_COMMENT_PARENT_PAGE_ERROR = 3;

    private VideoInfoVO videoInfoVO;

    @BindView(R.id.et_commend)
    TextView mCommendView;

    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    WrapRecyclerView mRecyclerView;

    @BindView(R.id.tv_comment_order_by)
    TextView mCommentOrderByTV;
    @BindView(R.id.tv_comment_count)
    TextView mCommentCountTV;
    @BindView(R.id.ll_order_by)
    LinearLayout mOrderByLayout;
    @BindView(R.id.tv_order_by_tip)
    TextView mOrderByTipTV;

    private VideoCommentParentAdapter mAdapter;
    private List<AppVideoUserCommentParentVO> myVideoUserCommentParentVOList;

    private String orderBy = "1";
    private int pageNum = 1;
    private int pageSize = 10;

    private Long commentCount = 0L;

    public static VideoCommentFragment newInstance() {
        return new VideoCommentFragment();
    }

    public void setVideoInfoVO(VideoInfoVO videoInfoVO) {
        this.videoInfoVO = videoInfoVO;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void initView() {
        showLoading();
        mAdapter = new VideoCommentParentAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        setOnClickListener(R.id.et_commend, R.id.ll_order_by);
    }

    @Override
    protected void lazyLoadData() {
        getVideoCommentParentPage(true);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_commend:
                // 评论弹窗
                new VideoCommendDialog.Builder(getAttachActivity())
                        .setCommendText(mCommendView.getText().toString())
                        .setListener(new VideoCommendDialog.OnListener() {
                            @Override
                            public void onSend(BaseDialog dialog, String content) {
                                mCommendView.clearComposingText();
                                // 评论视频
                                postCommentVideo(videoInfoVO.getVideoId(), content);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog, String content) {
                                mCommendView.setText(content);
                            }
                        })
                        .show();
                break;
            case R.id.ll_order_by:
                // 排序方式
                switch (orderBy) {
                    case "0":
                        orderBy = "1";
                        mAdapter.clearData();
                        showLoading();
                        getVideoCommentParentPage(true);
                        mCommentOrderByTV.setText(VideoCommentPageOrderEnum.LIKE_NUM.getDesc());
                        mOrderByTipTV.setText("按热度");
                        break;
                    case "1":
                        orderBy = "0";
                        mAdapter.clearData();
                        showLoading();
                        getVideoCommentParentPage(true);
                        mCommentOrderByTV.setText(VideoCommentPageOrderEnum.CREATE_TIME.getDesc());
                        mOrderByTipTV.setText("按时间");
                        break;
                    default:
                        break;
                }

            default:
                break;
        }
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

    /**
     * 获取提示布局
     */
    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getVideoCommentParentPage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        mRefreshLayout.setEnableLoadMore(true);
        getVideoCommentParentPage(true);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_VIDEO_COMMENT_PARENT_PAGE:
                    mCommentCountTV.setText(commentCount + "");
                    mAdapter.setData(myVideoUserCommentParentVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_COMMENT_PARENT_PAGE_MORE:
                    mAdapter.setMoreData(myVideoUserCommentParentVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_COMMENT_PARENT_PAGE_ERROR:
                    showError(v -> getVideoCommentParentPage(true));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 分页视频根评论
     *
     * @param isRefresh
     */
    public void getVideoCommentParentPage(boolean isRefresh) {
        EasyHttp.post(this).api(new VideoCommentParentPageApi()
                .setVideoId(videoInfoVO.getVideoId())
                .setOrderBy(orderBy)
                .setPageNum(pageNum)
                .setPageSize(pageSize)).request(new HttpCallback<PageDataInfo<AppVideoUserCommentParentVO>>(this.getAttachActivity()) {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSucceed(PageDataInfo<AppVideoUserCommentParentVO> rows) {
                if (rows.getTotal() == 0) {
                    mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                    return;
                }
                if (isRefresh) {
                    mRefreshLayout.finishRefresh(true);
                    myVideoUserCommentParentVOList = rows.getRows();
                    commentCount = rows.getTotal();
                    // 更新ui
                    mHandler.sendEmptyMessage(HANDLER_VIDEO_COMMENT_PARENT_PAGE);
                } else {
                    mRefreshLayout.finishLoadMore(true);
                    myVideoUserCommentParentVOList = rows.getRows();
                    mHandler.sendEmptyMessage(HANDLER_VIDEO_COMMENT_PARENT_PAGE_MORE);
                }
            }

            @Override
            public void onFail(Exception e) {
                showError(view -> getVideoCommentParentPage(isRefresh));
                TextView footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                footerView.setText("我也是有底线的");
                footerView.setOnClickListener(v -> toast("点击了尾部"));
            }
        });
    }

    /**
     * 评论视频
     */
    public void postCommentVideo(String videoId, String content) {
        EasyHttp.post(this)
                .api(new CommentVideoApi()
                        .setVideoId(videoId)
                        .setContent(content))
                .request(new HttpCallback<HttpData<Boolean>>(this.getAttachActivity()) {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        if (data.getCode() == 200) {
                            toast("评论成功");
                            getVideoCommentParentPage(true);
                        } else {
                            toast(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        toast("评论失败");
                    }
                });
    }
}