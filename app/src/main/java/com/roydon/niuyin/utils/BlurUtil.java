package com.roydon.niuyin.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class BlurUtil {

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);

        output.copyTo(bitmap);

        rs.destroy();

        return bitmap;
    }

    public static void blurLayoutBackground(Context context, String imgUrl, View view, Resources r) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .fitCenter()
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap blurredBitmap = BlurUtil.blurBitmap(context, resource, 25f); // 25f是模糊半径，可以根据需要调整

                        Drawable drawable = new BitmapDrawable(r, blurredBitmap);
                        view.setBackground(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Placeholder handling if needed
                    }
                });
    }
}
