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
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseDialog;
import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.behave.AppVideoUserCommentParentVO;
import com.roydon.niuyin.http.response.behave.MyFavoriteVideoVO;
import com.roydon.niuyin.utils.DateUtils;
import com.roydon.niuyin.utils.TimeUtils;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/2 20:50
 * @description 视频父评论adapter
 */
public class VideoCommentParentAdapter extends MyAdapter<AppVideoUserCommentParentVO> {

    private OnItemChildClickListener mOnItemChildClickListener;

    public VideoCommentParentAdapter(Context context) {
        super(context);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    @NonNull
    @Override
    public VideoCommentParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoCommentParentAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_video_comment_parent);
        }

        @BindView(R.id.iv_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_nickname)
        TextView mNicknameTV;
        @BindView(R.id.tv_publish_time)
        TextView mPublishTimeTV;
        @BindView(R.id.tv_comment_content)
        TextView mCommentContentTV;
        @BindView(R.id.ll_open_replay)
        LinearLayout openReplayLayout;
        @BindView(R.id.tv_reply_count)
        TextView replayCountTV;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            AppVideoUserCommentParentVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getAvatar() != null && !item.getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getNickName());
            mPublishTimeTV.setText(TimeUtils.getSmartTime(DateUtils.localDateTime2Long(item.getCreateTime())));
            mCommentContentTV.setText(item.getContent());
            // 是否有子评论
            if (item.getChildrenCount() > 0) {
                openReplayLayout.setVisibility(View.VISIBLE);
                replayCountTV.setText(item.getChildrenCount().toString());
                openReplayLayout.setOnClickListener(v -> mOnItemChildClickListener.onOpenReplay(v, position, item));
            } else {
                openReplayLayout.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemChildClickListener {

        /**
         * 展开回复
         */
        void onOpenReplay(View view, int position, AppVideoUserCommentParentVO item);

    }
}