package com.roydon.niuyin.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roydon.niuyin.R;
import com.roydon.niuyin.common.MyAdapter;
import com.roydon.niuyin.http.glide.GlideApp;
import com.roydon.niuyin.http.response.social.DynamicUser;

import butterknife.BindView;

/**
 * 关注用户动态
 */
public final class FollowDynamicAdapter extends MyAdapter<DynamicUser> {

    public FollowDynamicAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_follow_dynamic);
        }

        @BindView(R.id.iv_user_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_user_nickname)
        TextView mNicknameTV;
        @BindView(R.id.iv_dynamic_dot)
        ImageView mDynamicDotIV;

        @Override
        public void onBindView(int position) {
            DynamicUser item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getAvatar() != null && !item.getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getNickname());
            if (item.getHasRead()) {
                mDynamicDotIV.setVisibility(View.GONE);
            } else {
                mDynamicDotIV.setVisibility(View.VISIBLE);
            }
        }
    }
}