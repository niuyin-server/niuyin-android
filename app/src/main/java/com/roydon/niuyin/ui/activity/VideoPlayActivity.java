package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.VideoInfoApi;
import com.roydon.niuyin.http.response.VideoInfoVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.ui.adapter.HomeAdapter;
import com.roydon.niuyin.ui.adapter.VideoPlayAdapter;
import com.roydon.niuyin.ui.fragment.index.IndexFollowFragment;
import com.roydon.niuyin.ui.fragment.index.IndexHotFragment;
import com.roydon.niuyin.ui.fragment.index.IndexRecommendFragment;
import com.roydon.niuyin.ui.fragment.videoplay.VideoCommentFragment;
import com.roydon.niuyin.ui.fragment.videoplay.VideoInfoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoPlayActivity extends MyActivity {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_INFO = 1;

    @DebugLog
    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(IntentKey.VIDEO_ID, videoId);
        context.startActivity(intent);
    }

    private VideoInfoVO videoInfoVO;

    @BindView(R.id.videoPlayer)
    VideoView mVideoPlayerView;

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView() {
        // tab
        String[] mTitles = {"简介", "评论"};
        ArrayList<Fragment> mIndexFragments = new ArrayList<>();
        mIndexFragments.add(VideoInfoFragment.newInstance());
        mIndexFragments.add(VideoCommentFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mIndexFragments.size());
        mViewPager.setAdapter(new VideoPlayAdapter(getSupportFragmentManager(), mTitles, mIndexFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);
    }

    @Override
    protected void initData() {

        EasyHttp.get(this)
                .api(new VideoInfoApi()
                        .setVideoId(getString(IntentKey.VIDEO_ID)))
                .request(new HttpCallback<HttpData<VideoInfoVO>>(this) {

                    @Override
                    public void onSucceed(HttpData<VideoInfoVO> data) {
                        videoInfoVO = data.getData();
                        // 获取视频成功
                        mHandler.sendEmptyMessage(HANDLER_VIDEO_INFO);

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
                case HANDLER_VIDEO_INFO:
                    showVideoInfo(videoInfoVO);
                    break;
                default:
                    break;
            }
        }
    };

    private void showVideoInfo(VideoInfoVO videoInfoVO) {
        mVideoPlayerView.setUrl(videoInfoVO.getVideoUrl()); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(videoInfoVO.getVideoTitle(), false);
        mVideoPlayerView.setVideoController(controller); //设置控制器
        mVideoPlayerView.start(); //开始播放，不调用则不自动播放
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayerView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoPlayerView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayerView.release();
    }


    @Override
    public void onBackPressed() {
        if (!mVideoPlayerView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}