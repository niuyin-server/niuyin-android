package com.roydon.niuyin.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.domain.NoticeTypeBean;
import com.roydon.niuyin.enums.MessageType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.ParentVideoCategoryApi;
import com.roydon.niuyin.http.response.video.AppVideoCategoryVo;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.NoticeBehaveActivity;
import com.roydon.niuyin.ui.adapter.NoticeTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc   : 消息
 */
public final class FragmentMessage extends MyFragment<HomeActivity> implements BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_NOTICE_TYPE = 1;

    @BindView(R.id.rv_message)
    RecyclerView mRecyclerView;

    private NoticeTypeAdapter mAdapter;

    private List<NoticeTypeBean> noticeTypeBeanList = new ArrayList<>();

    public static FragmentMessage newInstance() {
        return new FragmentMessage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        mAdapter = new NoticeTypeAdapter(getContext());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        setOnClickListener();
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {
        initNoticeTypeView();
    }

    /**
     * 左项被点击
     *
     * @param v 被点击的左项View
     */
    @Override
    public void onLeftClick(View v) {
        AttachPopupView attachPopupView = new XPopup.Builder(getContext())
                .isCoverSoftInput(true)
                .hasShadowBg(false)
                .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(new String[]{"分享", "编辑", "发布即刻"},
                        new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                toast("click " + text);
                            }
                        }, 0, 0);
        attachPopupView.show();
    }

    private void initNoticeTypeView() {
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.FRIEND, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.BEHAVE, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.SYSTEM, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.ACTIVITY, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.SERVICE, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.ASSIGNMENT, "没有新通知"));
        noticeTypeBeanList.add(new NoticeTypeBean(MessageType.FUN, "没有新通知"));
        mHandler.sendEmptyMessage(HANDLER_NOTICE_TYPE);
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
                case HANDLER_NOTICE_TYPE:
                    mAdapter.setData(noticeTypeBeanList);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 当 RecyclerView 某个条目被点击时回调
     *
     * @param recyclerView RecyclerView 对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        NoticeTypeBean noticeTypeBean = noticeTypeBeanList.get(position);
        if (noticeTypeBean.getMessageType() == MessageType.FRIEND) {
            // 跳转到好友通知页面
            toast("friend");
//            FriendNoticeActivity.startActivity(this);
        } else if (noticeTypeBean.getMessageType() == MessageType.BEHAVE) {
            // 跳转到互动通知页面
            startActivity(NoticeBehaveActivity.class);
        }
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}