package com.roydon.niuyin.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.ui.fragment.videoplay.VideoCommentReplayFragment;

public class VideoCommentReplayFragmentStateAdapter extends FragmentStateAdapter {

    AppVideoUserCommentParentVO comment;

    public VideoCommentReplayFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, AppVideoUserCommentParentVO comment) {
        super(fragmentActivity);
        this.comment = comment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        VideoCommentReplayFragment videoCommentReplayFragment = VideoCommentReplayFragment.newInstance();
        videoCommentReplayFragment.setVideoUserCommentParent(comment);
        return videoCommentReplayFragment;
    }

    @Override
    public int getItemCount() {
        return 1; // 返回Fragment的数量
    }
}