package com.roydon.niuyin.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

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
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.model.PageDataInfo;
import com.roydon.niuyin.http.request.social.FollowDynamicApi;
import com.roydon.niuyin.http.request.social.InitUserInBoxApi;
import com.roydon.niuyin.http.request.social.VideoDynamicPageApi;
import com.roydon.niuyin.http.request.user.UserProfileApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.http.response.social.DynamicUser;
import com.roydon.niuyin.http.response.video.VideoVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.activity.FollowFansActivity;
import com.roydon.niuyin.ui.activity.HomeActivity;
import com.roydon.niuyin.ui.activity.UserProfileActivity;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.adapter.FollowDynamicAdapter;
import com.roydon.niuyin.ui.adapter.MeLikeAdapter;
import com.roydon.niuyin.ui.adapter.VideoImageDynamicAdapter;
import com.roydon.niuyin.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * desc   : 朋友
 * author  : roydon
 */
public final class FragmentFriend extends MyFragment<HomeActivity> implements StatusAction, OnRefreshLoadMoreListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_FOLLOW_DYNAMIC = 1;
    private static final int HANDLER_FOLLOW_DYNAMIC_MORE = 2;
    private static final int HANDLER_FOLLOW_DYNAMIC_ERROR = 3;
    private static final int HANDLER_VIDEO_DYNAMIC = 10;
    private static final int HANDLER_VIDEO_DYNAMIC_MORE = 11;
    private static final int HANDLER_VIDEO_DYNAMIC_ERROR = 12;

    @BindView(R.id.hintLayout)
    HintLayout mHintLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_friend_dynamic)
    LinearLayout mFriendDynamicLayout;

    // 好友动态
    @BindView(R.id.rv_follow_dynamics)
    WrapRecyclerView followDynamicRV;

    FollowDynamicAdapter followDynamicAdapter;
    private List<DynamicUser> dynamicUserList;

    // 视频动态
    @BindView(R.id.rv_video_dynamics)
    WrapRecyclerView videoDynamicRV;

    VideoImageDynamicAdapter videoImageDynamicAdapter;
    private List<VideoVO> videoVOList;

    private int pageNum = 1;
    private int pageSize = 10;

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
        showLoading();
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        // 用户动态
        followDynamicAdapter = new FollowDynamicAdapter(getContext());
        followDynamicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                UserProfileActivity.start(getContext(), dynamicUserList.get(position).getUserId());
            }
        });
        followDynamicRV.setAdapter(followDynamicAdapter);
        // 视频动态
        videoImageDynamicAdapter = new VideoImageDynamicAdapter(getContext());
        videoImageDynamicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                VideoVO item = videoImageDynamicAdapter.getItem(position);
                if ((PublishType.VIDEO.getCode()).equals(item.getPublishType())) {
                    // 视频
                    VideoPlayActivity.start(getContext(), item.getVideoId(), VideoScreenType.DEFAULT.getCode());
                } else if ((PublishType.IMAGE.getCode()).equals(item.getPublishType())) {
                    // 图文
                    VideoImagePlayActivity.start(getContext(), item.getVideoId());
                }
            }
        });
        videoDynamicRV.setAdapter(videoImageDynamicAdapter);
    }

    @Override
    protected void lazyLoadData() {
        getFollowDynamic();
        getVideoDynamic(true);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getVideoDynamic(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getFollowDynamic();
        pageNum = 1;
        getVideoDynamic(true);
        initUserInBox();
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
                    showComplete();
                    break;
                case HANDLER_FOLLOW_DYNAMIC_ERROR:
                    break;
                case HANDLER_VIDEO_DYNAMIC:
                    videoImageDynamicAdapter.setData(videoVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_DYNAMIC_MORE:
                    videoImageDynamicAdapter.setMoreData(videoVOList);
                    showComplete();
                    break;
                case HANDLER_VIDEO_DYNAMIC_ERROR:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取好友动态
     */
    private void getFollowDynamic() {
//        mFriendDynamicLayout.setVisibility(View.GONE);
        EasyHttp.get(this)
                .api(new FollowDynamicApi())
                .request(new HttpCallback<PageDataInfo<DynamicUser>>(getAttachActivity()) {

                    @Override
                    public void onSucceed(PageDataInfo<DynamicUser> data) {
                        List<DynamicUser> rows = data.getRows();
                        dynamicUserList = rows;
                        if (data.getTotal() == 0) {
                            mFriendDynamicLayout.setVisibility(View.GONE);
                        } else {
                            mFriendDynamicLayout.setVisibility(View.VISIBLE);
                        }
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_FOLLOW_DYNAMIC);
                    }
                });
    }

    /**
     * 获取视频动态
     */
    private void getVideoDynamic(boolean isRefresh) {
        EasyHttp.post(this)
                .api(new VideoDynamicPageApi()
                        .setPageNum(pageNum)
                        .setPageSize(pageSize))
                .request(new HttpCallback<PageDataInfo<VideoVO>>(getAttachActivity()) {

                    @Override
                    public void onSucceed(PageDataInfo<VideoVO> data) {
                        List<VideoVO> rows = data.getRows();
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh(true);
                            videoVOList = rows;
                            // 更新ui
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_DYNAMIC);
                        } else {
                            mRefreshLayout.finishLoadMore(true);
                            videoVOList = rows;
                            // 更新ui
                            mHandler.sendEmptyMessage(HANDLER_VIDEO_DYNAMIC_MORE);
                        }
//                        if (Objects.isNull(rows) || rows.isEmpty() || rows.size() < videoVOList.size()) {
//                            mRefreshLayout.setEnableLoadMore(false);
//                        }
//                        // 更新ui
//                        mHandler.sendEmptyMessage(HANDLER_VIDEO_DYNAMIC);
                    }
                });
    }

    /**
     * 初始化用户收件箱
     */
    private void initUserInBox() {
        EasyHttp.get(this)
                .api(new InitUserInBoxApi())
                .request(new HttpCallback<HttpData<Boolean>>(getAttachActivity()) {

                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        Boolean data1 = data.getData();
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