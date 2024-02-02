package com.roydon.niuyin.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public abstract class VideoImageBannerAdapter<T> extends BannerAdapter<T, BannerImageHolder> {

    public VideoImageBannerAdapter(List<T> mData) {
        super(mData);
    }

    /**
     * fitXY: 图片将被拉伸以填充整个 ImageView，不保持原始宽高比。这可能导致图片变形。
     * centerCrop: 图片按比例缩放，保持宽高比，并且至少填充 ImageView 的一个维度。图片会被裁剪以适应 ImageView，使得 ImageView 完全填满，但可能会裁剪掉部分图片。
     * centerInside: 图片按比例缩放，保持宽高比，并且完整显示在 ImageView 中。如果图片尺寸大于 ImageView，图片将缩小以适应 ImageView；如果图片尺寸小于 ImageView，图片将保持原始大小。
     * fitCenter: 图片按比例缩放，保持宽高比，并且完整显示在 ImageView 中。如果图片尺寸大于 ImageView，图片将缩小以适应 ImageView；如果图片尺寸小于 ImageView，图片将保持原始大小。
     * center: 图片保持原始大小，并居中显示在 ImageView 中。如果图片尺寸大于 ImageView，图片将被截断。
     * matrix: 图片将按照矩阵变换进行缩放和平移。你可以使用 setImageMatrix() 方法自定义变换矩阵。
     */
    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return new BannerImageHolder(imageView);
    }

}
