package com.roydon.niuyin.ui.activity;

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
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.helper.SPUtils;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.user.UserInfoApi;
import com.roydon.niuyin.http.request.video.ParentVideoCategoryApi;
import com.roydon.niuyin.http.response.member.MemberInfoVO;
import com.roydon.niuyin.http.response.video.AppVideoCategoryVo;
import com.roydon.niuyin.http.response.video.VideoRecommendVO;
import com.roydon.niuyin.ui.adapter.RecommendVideoAdapter;
import com.roydon.niuyin.ui.adapter.VideoCategoryAdapter;
import com.roydon.niuyin.widget.HintLayout;

import java.util.List;

import butterknife.BindView;

public class VideoCategoryActivity extends MyActivity implements StatusAction, BaseAdapter.OnItemClickListener {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_PARENT_VIDEO_CATEGORY = 1;

    @BindView(R.id.hl_status_hint)
    HintLayout mHintLayout;
    @BindView(R.id.rv_status_list)
    WrapRecyclerView mRecyclerView;

    private VideoCategoryAdapter mAdapter;

    private List<AppVideoCategoryVo> videoCategoryVoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_category;
    }

    @Override
    protected void initView() {
        showLoading();
        mAdapter = new VideoCategoryAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        getParentVideoCategory();
    }

    private void getParentVideoCategory() {
        EasyHttp.get(this)
                .api(new ParentVideoCategoryApi())
                .request(new HttpCallback<HttpData<List<AppVideoCategoryVo>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppVideoCategoryVo>> data) {
                        videoCategoryVoList = data.getData();
                        // 更新ui
                        mHandler.sendEmptyMessage(HANDLER_PARENT_VIDEO_CATEGORY);
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
                case HANDLER_PARENT_VIDEO_CATEGORY:
                    mAdapter.setData(videoCategoryVoList);
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

    }
}