package com.roydon.niuyin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.DebugLog;
import com.roydon.niuyin.common.MyActivity;
import com.roydon.niuyin.other.IntentKey;

import butterknife.BindView;

public class VideoImagePlayActivity extends MyActivity {

    @DebugLog
    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, VideoImagePlayActivity.class);
        intent.putExtra(IntentKey.VIDEO_ID, videoId);
        context.startActivity(intent);
    }

    @BindView(R.id.tv_video_id)
    TextView mVideoIdView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_image_play;
    }

    @Override
    protected void initView() {
        mVideoIdView.setText(getString(IntentKey.VIDEO_ID));
    }

    @Override
    protected void initData() {

    }
}