package com.roydon.niuyin.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.roydon.niuyin.R;
import com.roydon.niuyin.action.StatusAction;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.social.FollowDynamicApi;
import com.roydon.niuyin.http.request.user.UserProfileApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.http.response.social.DynamicUser;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.adapter.FollowDynamicAdapter;
import com.roydon.niuyin.ui.adapter.MeLikeAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * desc   : 朋友
 * author  : roydon
 */
public final class FragmentFriend extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_FOLLOW_DYNAMIC = 1;
    private static final int HANDLER_FOLLOW_DYNAMIC_ERROR = 2;

    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_follow_dynamics)
    WrapRecyclerView followDynamicRV;

    FollowDynamicAdapter followDynamicAdapter;
    private List<DynamicUser> dynamicUserList;

    public static FragmentFriend newInstance() {
        return new FragmentFriend();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend;
    }

    /**
     * 获取提示布局
     */
    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void initView() {
        followDynamicAdapter = new FollowDynamicAdapter(getContext());
        followDynamicAdapter.setOnItemClickListener(this);
        followDynamicRV.setAdapter(followDynamicAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void lazyLoadData() {
        getFollowDynamic();
    }

    @Override
    protected void initData() {
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
        DynamicUser item = dynamicUserList.get(position);
        toast(item.getNickname());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getFollowDynamic();
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
                case HANDLER_FOLLOW_DYNAMIC:
                    followDynamicAdapter.setData(dynamicUserList);
                    mRefreshLayout.finishRefresh();
                    showComplete();
                    break;
                case HANDLER_FOLLOW_DYNAMIC_ERROR:
                    break;
                default:
                    break;
            }
        }
    };

    private void getFollowDynamic() {
        EasyHttp.get(this)
                .api(new FollowDynamicApi())
                .request(new HttpCallback<PageDataInfo<DynamicUser>>(getAttachActivity()) {

                    @Override
                    public void onSucceed(PageDataInfo<DynamicUser> data) {
                        List<DynamicUser> rows = data.getRows();
                        dynamicUserList = rows;
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_FOLLOW_DYNAMIC);
                    }
                });
    }

    @SingleClick
    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}