package com.roydon.niuyin.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;
import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.ui.adapter.VideoCommentReplayFragmentStateAdapter;

/**
 * @author roydon
 * @date 2024/1/30 20:59
 * @description 视频评论回复弹窗
 */
@SuppressLint("NonConstantResourceId")
public class VideoCommentReplayDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        private OnListener mListener;

        private AppVideoUserCommentParentVO appVideoUserCommentParentVO;

        ViewPager2 mViewPager;

        FragmentManager fragmentManager;

        @SuppressLint("ClickableViewAccessibility")
        public Builder(Context context, AppVideoUserCommentParentVO comment, FragmentActivity fragmentActivity) {
            super(context);
            setContentView(R.layout.dialog_video_comment_replay);
            setHeight(getResources().getDisplayMetrics().heightPixels - (getResources().getDisplayMetrics().widthPixels * 10 / 16) - 90);

            mViewPager = findViewById(R.id.viewPager);
            FragmentStateAdapter fragmentStateAdapter = new VideoCommentReplayFragmentStateAdapter(fragmentActivity,comment);
            mViewPager.setAdapter(fragmentStateAdapter);
        }

        public Builder setListener(OnListener mListener) {
            this.mListener = mListener;
            return this;
        }

//        public Builder setParentComment() {
//            this.appVideoUserCommentParentVO = comment;
//            return this;
//        }
//
//        public Builder setViewPager() {
//
//            return this;
//        }
    }

    public interface OnListener {

        /**
         * 选择条目时回调
         */
        void onSelected(BaseDialog dialog, int position, AppVideoUserCommentParentVO bean);
    }

}
