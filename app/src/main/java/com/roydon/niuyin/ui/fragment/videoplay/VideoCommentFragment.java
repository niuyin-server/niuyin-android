package com.roydon.niuyin.ui.fragment.videoplay;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description niuyin-android
 */
public class VideoCommentFragment extends MyFragment<VideoPlayActivity> {

    public static VideoCommentFragment newInstance() {
        return new VideoCommentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}