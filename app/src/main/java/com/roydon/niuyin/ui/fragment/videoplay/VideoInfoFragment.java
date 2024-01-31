package com.roydon.niuyin.ui.fragment.videoplay;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyFragment;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.VideoInfoVO;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/1/31 23:51
 * @description niuyin-android
 */
public class VideoInfoFragment extends MyFragment<VideoPlayActivity> {

    @BindView(R.id.ll_author)
    LinearLayout mAuthorLayout;
    @BindView(R.id.iv_author_avatar)
    ImageView mAuthorAvatarView;
    @BindView(R.id.tv_author_nickname)
    TextView mAuthorNicknameView;

    private VideoInfoVO videoInfoVO;


    public static VideoInfoFragment newInstance() {
        return new VideoInfoFragment();
    }

    public void setVideoInfoVO(VideoInfoVO videoInfoVO) {
        this.videoInfoVO = videoInfoVO;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_info;
    }

    @Override
    protected void initView() {
        GlideApp.with(this)
                .load(videoInfoVO.getAuthor().getAvatar())
                .circleCrop()
                .into(mAuthorAvatarView);
        mAuthorNicknameView.setText(videoInfoVO.getAuthor().getNickName());
        mAuthorLayout.setOnClickListener(v -> {

        });
    }

    @Override
    protected void initData() {

    }
}