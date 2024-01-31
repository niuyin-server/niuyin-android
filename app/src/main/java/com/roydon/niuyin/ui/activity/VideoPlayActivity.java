package com.roydon.niuyin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.other.IntentKey;

import butterknife.BindView;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoPlayActivity extends MyActivity {

    @DebugLog
    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(IntentKey.VIDEO_ID, videoId);
        context.startActivity(intent);
    }

    @BindView(R.id.videoPlayer)
    VideoView mVideoPlayerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView() {
        // 移除监听器以避免重复调用
//        mVideoPlayerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        // 获取视图的宽度
//        int width = mVideoPlayerView.getWidth();
//
//        // 计算高度的一半
//        int height = width * 9 / 16;
//
//        // 设置视图的高度
//        ViewGroup.LayoutParams layoutParams = mVideoPlayerView.getLayoutParams();
//        layoutParams.height = height;
//        mVideoPlayerView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {

        String VIDEO_URL = "https://niuyin-server.oss-cn-shenzhen.aliyuncs.com/video/2023/12/08/11668a30db5349a5843a1e39d224ef9a.mp4";
        mVideoPlayerView.setUrl(VIDEO_URL); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("“盘点地平线4里的绝美风景”", false);
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