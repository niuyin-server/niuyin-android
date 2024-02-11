package com.roydon.niuyin.ui.fragment.videoplay;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;
import com.roydon.niuyin.aop.SingleClick;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;
import com.roydon.niuyin.ui.dialog.VideoCommendDialog;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description niuyin-android
 */
@SuppressLint("NonConstantResourceId")
public class VideoCommentFragment extends MyFragment<VideoPlayActivity> {

    @BindView(R.id.et_commend)
    TextView mCommendView;

    public static VideoCommentFragment newInstance() {
        return new VideoCommentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.et_commend);
    }

    @Override
    protected void lazyLoadData() {
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_commend:
                // 评论弹窗
                new VideoCommendDialog.Builder(getAttachActivity())
                        .setCommendText(mCommendView.getText().toString())
                        .setListener(new VideoCommendDialog.OnListener() {
                            @Override
                            public void onSend(BaseDialog dialog, String content) {
                                toast(content);
                                mCommendView.clearComposingText();
                            }

                            @Override
                            public void onCancel(BaseDialog dialog, String content) {
                                mCommendView.setText(content);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}