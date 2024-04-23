package com.roydon.niuyin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.RatioFrameLayout;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.http.model.HttpData;
import com.roydon.niuyin.http.request.video.VideoInfoApi;
import com.roydon.niuyin.http.response.video.VideoInfoVO;
import com.roydon.niuyin.other.IntentKey;
import com.roydon.niuyin.other.MediaVideoInfo;
import com.roydon.niuyin.ui.adapter.VideoPlayAdapter;
import com.roydon.niuyin.ui.fragment.videoplay.VideoCommentFragment;
import com.roydon.niuyin.ui.fragment.videoplay.VideoInfoFragment;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoPlayActivity extends MyActivity {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_VIDEO_INFO = 1;

    @DebugLog
    public static void start(Context context, String videoId, String videoScreenType) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(IntentKey.VIDEO_ID, videoId);
        intent.putExtra(IntentKey.VIDEO_SCREEN_TYPE, videoScreenType);
        context.startActivity(intent);
    }

    private VideoInfoVO videoInfoVO;

    @BindView(R.id.rl_screen_scale)
    RatioFrameLayout mScreenScaleLayout;
    @BindView(R.id.videoPlayer)
    VideoView mVideoPlayerView;
    @BindView(R.id.iv_video_status)
    ImageView mVideoStatusView;

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
        getStatusBarConfig().statusBarDarkFont(false).statusBarColor(R.color.black);
        mVideoStatusView.setVisibility(View.GONE);
        if (getString(IntentKey.VIDEO_SCREEN_TYPE) != null) {
            if (VideoScreenType.HENG.getCode().equals(getString(IntentKey.VIDEO_SCREEN_TYPE))) {
                mScreenScaleLayout.setSizeRatio(1.6f);
            } else if (VideoScreenType.SHU.getCode().equals(getString(IntentKey.VIDEO_SCREEN_TYPE))) {
                mScreenScaleLayout.setSizeRatio(0.75f);
            } else if (VideoScreenType.SQUARE.getCode().equals(getString(IntentKey.VIDEO_SCREEN_TYPE))) {
                mScreenScaleLayout.setSizeRatio(1f);
            } else if (VideoScreenType.DEFAULT.getCode().equals(getString(IntentKey.VIDEO_SCREEN_TYPE))) {
//                mScreenScaleLayout.setSizeRatio(1f);
            }
        }
    }

//    public void reLoadNewVideo(String videoId) {
//        this.apiGetVideoInfo(videoId);
//    }

    @Override
    protected void initData() {
        apiGetVideoInfo(getString(IntentKey.VIDEO_ID));

    }

    private void apiGetVideoInfo(String videoId) {
        EasyHttp.get(this)
                .api(new VideoInfoApi()
                        .setVideoId(videoId))
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
//                    setScreenLayout(videoInfoVO);
                    showVideoInfo(videoInfoVO);
                    break;
                default:
                    break;
            }
        }
    };

    private void setScreenLayout(VideoInfoVO videoInfoVO) {
        // 设置视频比例 4:3竖屏视频
        if (!Objects.isNull(videoInfoVO.getVideoInfo())) {
            MediaVideoInfo mediaVideoInfo = new Gson().fromJson(videoInfoVO.getVideoInfo(), MediaVideoInfo.class);
            if (mediaVideoInfo.getWidth() > mediaVideoInfo.getHeight()) {
                // 横屏 1.6
                toast("横屏视频");
                mScreenScaleLayout.setSizeRatio(1.6f);
            } else if (mediaVideoInfo.getHeight() > mediaVideoInfo.getWidth()) {
                // 竖屏 0.75
                toast("竖屏视频");
                mScreenScaleLayout.setSizeRatio(0.75f);
            }
        }
    }

    private void showVideoInfo(VideoInfoVO videoInfoVO) {

//        BlurUtil.blurLayoutBackground(this, videoInfoVO.getCoverImage(), mScreenScaleLayout, getResources());

        mVideoPlayerView.setUrl(videoInfoVO.getVideoUrl()); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(videoInfoVO.getVideoTitle(), false);
        mVideoPlayerView.setVideoController(controller); //设置控制器
        mVideoPlayerView.start(); //开始播放，不调用则不自动播放


        // tab
        String[] mTitles = {"简介", "评论"};
        ArrayList<Fragment> mIndexFragments = new ArrayList<>();
        VideoInfoFragment videoInfoFragment = VideoInfoFragment.newInstance();
        videoInfoFragment.setVideoInfoVO(videoInfoVO);
        VideoCommentFragment videoCommentFragment = VideoCommentFragment.newInstance();
        videoCommentFragment.setVideoInfoVO(videoInfoVO);
        mIndexFragments.add(videoInfoFragment);
        mIndexFragments.add(videoCommentFragment);
        mViewPager.setOffscreenPageLimit(mIndexFragments.size());
        mViewPager.setAdapter(new VideoPlayAdapter(getSupportFragmentManager(), mTitles, mIndexFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCurrentTab(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayerView.pause();
//        mVideoStatusView.setVisibility(View.VISIBLE);
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