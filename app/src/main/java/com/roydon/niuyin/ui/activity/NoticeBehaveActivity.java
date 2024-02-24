package com.roydon.niuyin.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.domain.NoticeBehaveShowTypeBean;
import com.roydon.niuyin.enums.NoticeBehaveShowType;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.notice.BehaveNoticePageApi;
import com.roydon.niuyin.http.response.notice.NoticeVO;
import com.roydon.niuyin.ui.adapter.NoticeBehaveMessageAdapter;
import com.roydon.niuyin.ui.dialog.NoticeBehaveTypeDialog;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class NoticeBehaveActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_NOTICE_BEHAVE_PAGE = 1;

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rl_status_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    private NoticeBehaveMessageAdapter mAdapter;

    private List<NoticeVO> noticeVOList;

    private String noticeType = "";
    private int pageNum = 1;
    private int pageSize = 20;

    TextView footerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_behave;
    }

    @Override
    protected void initView() {
        showLoading();
        mAdapter = new NoticeBehaveMessageAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected void initData() {
        getBehaveNoticePage(true);
    }

    /**
     * 分页行为通知
     *
     * @param isRefresh
     */
    private void getBehaveNoticePage(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new BehaveNoticePageApi()
                        .setNoticeType(noticeType)
                        .setPageNum(pageNum)
                        .setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<NoticeVO>>(this) {
                    @Override
                    public void onSucceed(PageDataInfo<NoticeVO> rows) {
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            noticeVOList = rows.getRows();
                            if (Objects.isNull(rows.getRows()) || rows.getRows().isEmpty() || rows.getRows().size() < pageSize) {
                                mRefreshLayout.setNoMoreData(true);
                                footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                                footerView.setText("— 我也是有底线的 —");
                                footerView.setOnClickListener(v -> toast("点击了尾部"));
                            }
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            noticeVOList.addAll(rows.getRows());
                            if (Objects.isNull(rows.getRows()) || rows.getRows().isEmpty() || rows.getRows().size() < pageSize) {
                                mRefreshLayout.setNoMoreData(true);
                                footerView = mRecyclerView.addFooterView(R.layout.item_recycler_footer);
                                footerView.setText("— 我也是有底线的 —");
                                footerView.setOnClickListener(v -> toast("点击了尾部"));
                            }
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_NOTICE_BEHAVE_PAGE);
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
                case HANDLER_NOTICE_BEHAVE_PAGE:
                    mAdapter.setData(noticeVOList);
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
        NoticeVO item = mAdapter.getItem(position);
        if (item != null) {
            toast(item.getVideoId());
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getBehaveNoticePage(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        mRefreshLayout.setEnableLoadMore(true);
        mRecyclerView.removeFooterView(footerView);
        getBehaveNoticePage(true);
    }

    @Override
    public void onRightClick(View v) {
        List<NoticeBehaveShowTypeBean> noticeBehaveShowTypeBeans = new ArrayList<>();
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.All));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.LIKE));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.FOLLOW));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.FAVORITE));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.COMMENT_ADD));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.COMMENT_REPLAY));
        noticeBehaveShowTypeBeans.add(new NoticeBehaveShowTypeBean(NoticeBehaveShowType.COMMENT_LIKE));
        new NoticeBehaveTypeDialog.Builder(this)
                .setList(noticeBehaveShowTypeBeans)
                .setListener(new NoticeBehaveTypeDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int position, Object o) {
                        NoticeBehaveShowTypeBean behaveTypeBean = (NoticeBehaveShowTypeBean) o;
                        dialog.dismiss();
                        pageNum = 1;
                        noticeType = behaveTypeBean.getNoticeBehaveType().getCode();
                        mRefreshLayout.setEnableLoadMore(true);
                        mRecyclerView.removeFooterView(footerView);
                        titleBar.setTitle(behaveTypeBean.getNoticeBehaveType().getInfo());
                        showLoading();
                        getBehaveNoticePage(true);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                })
                .show();
    }
}