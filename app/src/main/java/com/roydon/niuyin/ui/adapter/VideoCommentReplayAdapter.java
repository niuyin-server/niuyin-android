package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.behave.VideoCommentReplayVO;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/2 20:50
 * @description 视频根评论回复评论adapter
 */
public class VideoCommentReplayAdapter extends MyAdapter<VideoCommentReplayVO> {

    private OnItemChildClickListener mOnItemChildClickListener;

    public VideoCommentReplayAdapter(Context context) {
        super(context);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    @NonNull
    @Override
    public VideoCommentReplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoCommentReplayAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_comment_replay);
        }

        @BindView(R.id.iv_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_nickname)
        TextView mNicknameTV;
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeTV;
        @BindView(R.id.tv_comment_content)
        TextView mCommentContentTV;
        @BindView(R.id.tv_replay_nick_name)
        TextView replayNickNameTV;
        @BindView(R.id.ll_replay_user)
        LinearLayout replayUserLayout;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            VideoCommentReplayVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getAvatar() != null && !item.getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getNickName());
            mPublishTimeTV.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(item.getCreateTime())));
            mCommentContentTV.setText(item.getContent());
            if (item.getReplayUserId() != null) {
                replayUserLayout.setVisibility(View.VISIBLE);
                replayNickNameTV.setText("@" + item.getReplayUserNickName());
            } else {
                replayUserLayout.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemChildClickListener {

        /**
         * 展开回复
         */
        void onOpenReplay(View view, int position, VideoCommentReplayVO item);

        /**
         * 回复评论
         */
        void onReplayComment(View view, int position, VideoCommentReplayVO item);

    }
}