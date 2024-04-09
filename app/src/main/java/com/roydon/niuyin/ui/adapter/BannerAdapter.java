package com.roydon.niuyin.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.roydon.niuyin.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * 轮播适配器
 */
public class BannerAdapter extends BaseBannerAdapter<String> {

    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        ImageView imageView = holder.findViewById(R.id.banner_image);
        Glide.with(imageView).load(data).into(imageView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner;
    }
}

