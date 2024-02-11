package com.roydon.niuyin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.notice.NoticeVO;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/11 19:35
 * @description 互动消息适配器
 */
public class NoticeBehaveMessageAdapter extends MyAdapter<NoticeVO> {

    public NoticeBehaveMessageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public NoticeBehaveMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeBehaveMessageAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_notice_behave_message);
        }

        @BindView(R.id.iv_user_avatar)
        ImageView mUserAvatarTV;
        @BindView(R.id.iv_notice_type)
        ImageView mNoticeTypeIV;
        @BindView(R.id.tv_user_nickname)
        TextView mUserNicknameTV;
        @BindView(R.id.tv_notice_desc)
        TextView mNoticeDescTV;
        @BindView(R.id.cv_video_cover)
        CardView mVideoCoverCV;
        @BindView(R.id.iv_video_cover)
        ImageView mVideoCoverIV;
        @BindView(R.id.btn_follow)
        Button mFollowBtn;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            NoticeVO item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getOperateAvatar() != null && !item.getOperateAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getOperateAvatar()).into(mUserAvatarTV);
            }
            mNoticeTypeIV.setVisibility(View.VISIBLE);
            mVideoCoverCV.setVisibility(View.VISIBLE);
            mFollowBtn.setVisibility(View.GONE);
            switch (item.getNoticeType()) {
                case "0":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_like);
                    break;
                case "1":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_follow);
                    mVideoCoverCV.setVisibility(View.GONE);
                    mFollowBtn.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_favorite);
                    break;
                case "3":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_commend);
                    break;
                case "4":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_commend);
                    break;
                case "5":
                    mNoticeTypeIV.setImageResource(R.drawable.notice_commend);
                    break;
                default:
                    break;
            }
            if (item.getNickName() != null && !item.getNickName().equals("")) {
                mUserNicknameTV.setText(item.getNickName());
            }
            mNoticeDescTV.setText(item.getContent());
            if (item.getVideoCoverImage() != null && !item.getVideoCoverImage().equals("")) {
                GlideApp.with(getContext()).load(item.getVideoCoverImage()).into(mVideoCoverIV);
            }
        }
    }
}