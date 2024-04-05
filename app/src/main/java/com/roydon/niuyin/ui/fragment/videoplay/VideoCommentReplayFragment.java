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
import com.roydon.niuyin.http.request.behave.VideoCommentParentPageApi;
import com.roydon.niuyin.http.request.behave.VideoCommentReplayPageApi;
import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.http.response.behave.VideoCommentReplayVO;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.VideoCommentParentAdapter;
import com.roydon.niuyin.ui.adapter.VideoCommentReplayAdapter;
import com.roydon.niuyin.ui.dialog.VideoCommendDialog;
import com.roydon.niuyin.ui.dialog.VideoCommentReplayDialog;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description 视频播放页-视频评论回复评论Fragment
 */
@SuppressLint("NonConstantResourceId")
public class VideoCommentReplayFragment extends MyFragment<VideoPlayActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_COMMENT_REPLAY_PAGE = 1;
    private static final int HANDLER_VIDEO_COMMENT_REPLAY_PAGE_MORE = 2;
    private static final int HANDLER_VIDEO_COMMENT_REPLAY_PAGE_ERROR = 3;

    private AppVideoUserCommentParentVO videoUserCommentParent;

    @BindView(R.id.et_commend)
    TextView mCommendView;

    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    WrapRecyclerView mRecyclerView;

//    @BindView(R.id.tv_comment_order_by)
//    TextView mCommentOrderByTV;
//    @BindView(R.id.tv_comment_count)
//    TextView mCommentCountTV;
//    @BindView(R.id.ll_order_by)
//    LinearLayout mOrderByLayout;
//    @BindView(R.id.tv_order_by_tip)
//    TextView mOrderByTipTV;

    private VideoCommentReplayAdapter mAdapter;
    private List<VideoCommentReplayVO> mVideoCommentReplayVOList;

    private String orderBy = "1";
    private int pageNum = 1;
    private int pageSize = 10;

    private Long commentCount = 0L;

    public static VideoCommentReplayFragment newInstance() {
        return new VideoCommentReplayFragment();
    }

    public void setVideoUserCommentParent(AppVideoUserCommentParentVO parent) {
        this.videoUserCommentParent = parent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_comment_replay;
    }

    @Override
    protected void initView() {
        showLoading();
        mAdapter = new VideoCommentReplayAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        setOnClickListener(R.id.et_commend);
    }

    @Override
    protected void lazyLoadData() {
        getVideoCommentReplayPage(true);
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
//                                postCommentVideo(videoInfoVO.getVideoId(), content);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog, String content) {
                                mCommendView.setText(content);
                            }
                        })
                        .show();
                break;
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
        getVideoCommentReplayPage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        mRefreshLayout.setEnableLoadMore(true);
        getVideoCommentReplayPage(true);
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
                case HANDLER_VIDEO_COMMENT_REPLAY_PAGE:
                    mAdapter.setData(mVideoCommentReplayVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_COMMENT_REPLAY_PAGE_MORE:
                    mAdapter.setMoreData(mVideoCommentReplayVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_COMMENT_REPLAY_PAGE_ERROR:
                    showError(v -> getVideoCommentReplayPage(true));
                    break;
                default:
                    break;
            }
        }
    };


    private void getVideoCommentReplayPage(boolean isRefresh) {
        EasyHttp.post(getActivity()).api(new VideoCommentReplayPageApi()
                        .setCommentId(videoUserCommentParent.getCommentId())
                        .setOrderBy(orderBy)
                        .setPageNum(pageNum)
                        .setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<VideoCommentReplayVO>>(getAttachActivity()) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(PageDataInfo<VideoCommentReplayVO> rows) {
                        if (rows.getTotal() == 0) {
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                            return;
                        }
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            mVideoCommentReplayVOList = rows.getRows();
                            // 更新ui
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_COMMENT_REPLAY_PAGE);
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            mVideoCommentReplayVOList = rows.getRows();
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_COMMENT_REPLAY_PAGE_MORE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 评论视频
     */
//    public void postCommentVideo(String videoId, String content) {
//        EasyHttp.post(this)
//                .api(new CommentVideoApi()
//                        .setVideoId(videoId)
//                        .setContent(content))
//                .request(new HttpCallback<HttpData<Boolean>>(this.getAttachActivity()) {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onSucceed(HttpData<Boolean> data) {
//                        if (data.getCode() == 200) {
//                            toast("评论成功");
//                            getVideoCommentParentPage(true);
//                        } else {
//                            toast(data.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//                        toast("评论失败");
//                    }
//                });
//    }
}