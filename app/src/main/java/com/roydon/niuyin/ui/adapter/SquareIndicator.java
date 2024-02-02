package com.roydon.niuyin.ui.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.youth.banner.indicator.BaseIndicator;

/**
 * @author roydon
 * @date 2024/2/2 17:57
 * @description niuyin-android
 */
public class SquareIndicator extends BaseIndicator {
    private int mNormalRadius;
    private int mSelectedRadius;
    private int maxRadius;

    private int leftOffset; // 新增的变量

    public SquareIndicator(Context context) {
        this(context, null);
    }

    public SquareIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNormalRadius = config.getNormalWidth() / 2;
        mSelectedRadius = config.getSelectedWidth() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int indicatorWidth = config.getSelectedWidth() + (config.getNormalWidth() + config.getIndicatorSpace()) * (count - 1);
        leftOffset = (totalWidth - indicatorWidth) / 2; // 更新 leftOffset 的值
//        setMeasuredDimension(totalWidth, Math.max(config.getNormalWidth(), config.getSelectedWidth()));
        setMeasuredDimension(totalWidth, 8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }

        float left = leftOffset; // 使用 leftOffset
        for (int i = 0; i < count; i++) {
            mPaint.setColor(config.getCurrentPosition() == i ? config.getSelectedColor() : config.getNormalColor());
            int indicatorWidth = config.getCurrentPosition() == i ? config.getSelectedWidth() : config.getNormalWidth();
//            int indicatorHeight = Math.max(config.getNormalWidth(), config.getSelectedWidth());
            int indicatorHeight = 8;
            canvas.drawRect(left, 0, left + indicatorWidth, indicatorHeight, mPaint);
            left += indicatorWidth + config.getIndicatorSpace();
        }
    }

}
