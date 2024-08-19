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

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.social.FollowUser;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/2 20:50
 * @description 关注用户adapter
 */
public class FollowUserAdapter extends MyAdapter<FollowUser> {

    private OnItemChildClickListener mOnItemChildClickListener;

    public FollowUserAdapter(Context context) {
        super(context);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    @NonNull
    @Override
    public FollowUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FollowUserAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_follow_user);
        }

        @BindView(R.id.iv_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_nickname)
        TextView mNicknameTV;
        @BindView(R.id.btn_cancel_follow)
        Button mCancelFollowBTN;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            FollowUser item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getAvatar() != null && !item.getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getNickName());
            mCancelFollowBTN.setOnClickListener(v -> mOnItemChildClickListener.onCancelFollow(v, position, item));
        }
    }

    public interface OnItemChildClickListener {

        /**
         * 取消关注
         */
        void onCancelFollow(View view, int position, FollowUser item);

    }
}