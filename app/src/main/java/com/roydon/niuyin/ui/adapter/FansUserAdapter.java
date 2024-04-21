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
import com.roydon.niuyin.http.response.social.Fans;
import com.roydon.niuyin.http.response.social.FollowUser;

import butterknife.BindView;

/**
 * @author roydon
 * @date 2024/2/2 20:50
 * @description 粉丝用户adapter
 */
public class FansUserAdapter extends MyAdapter<Fans> {

    private OnItemChildClickListener mOnItemChildClickListener;

    public FansUserAdapter(Context context) {
        super(context);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    @NonNull
    @Override
    public FansUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FansUserAdapter.ViewHolder();
    }

    final class ViewHolder extends MyAdapter.ViewHolder {

        ViewHolder() {
            super(R.layout.item_fans_user);
        }

        @BindView(R.id.iv_avatar)
        ImageView mAvatarIV;
        @BindView(R.id.tv_nickname)
        TextView mNicknameTV;
        @BindView(R.id.btn_return_follow)
        Button mReturnFollowBTN;
        @BindView(R.id.btn_fans)
        Button mFansBTN;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            Fans item = getItem(position);
            if (item == null) {
                return;
            }
            if (item.getAvatar() != null && !item.getAvatar().equals("")) {
                GlideApp.with(getContext()).load(item.getAvatar()).into(mAvatarIV);
            }
            mNicknameTV.setText(item.getNickName());
            mReturnFollowBTN.setOnClickListener(v -> mOnItemChildClickListener.onReturnFollow(v, position, item));
            mFansBTN.setOnClickListener(v -> mOnItemChildClickListener.onMore(v, position, item));
            if (item.getWeatherFollow()) {
                mReturnFollowBTN.setVisibility(View.GONE);
                mFansBTN.setVisibility(View.VISIBLE);
            } else {
                mReturnFollowBTN.setVisibility(View.VISIBLE);
                mFansBTN.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemChildClickListener {

        /**
         * 回关
         */
        void onReturnFollow(View view, int position, Fans item);

        /**
         * 已互粉
         */
        void onMore(View view, int position, Fans item);

    }
}