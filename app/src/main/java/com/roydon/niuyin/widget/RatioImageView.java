package com.roydon.niuyin.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class RatioImageView extends AppCompatImageView {
    private static final float MIN_RATIO = 16f / 9f; // 最小宽高比
    private static final float MAX_RATIO = 9f / 16f; // 最大宽高比

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 计算基于宽度的期望高度
        float desiredHeightBasedOnWidth = widthSize * MIN_RATIO;
        // 计算基于高度的期望宽度
        float desiredWidthBasedOnHeight = heightSize / MAX_RATIO;

        // 根据MIN和MAX比率确定最终尺寸
        if (desiredHeightBasedOnWidth > heightSize && desiredWidthBasedOnHeight < widthSize) {
            // 如果基于宽度计算的高度大于当前高度，且基于高度计算的宽度小于当前宽度，则使用宽度作为基准
            setMeasuredDimension(widthSize, (int) desiredHeightBasedOnWidth);
        } else {
            // 否则使用高度作为基准
            setMeasuredDimension((int) desiredWidthBasedOnHeight, heightSize);
        }
    }
}