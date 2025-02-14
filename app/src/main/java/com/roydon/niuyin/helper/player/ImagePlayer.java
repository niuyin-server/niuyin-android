package com.roydon.niuyin.helper.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.roydon.niuyin.databinding.ViewPlayviewBinding;

/**
 * @author roydon
 * @date 2025/2/14 15:08
 * @description niuyin-android
 */
public class ImagePlayer extends FrameLayout implements DefaultLifecycleObserver {

    public ImagePlayer(@NonNull Context context) {
        super(context);
        initPlayer(context);
    }

    private void initPlayer(Context context) {
        ViewPlayviewBinding binding = ViewPlayviewBinding.inflate(LayoutInflater.from(context), this, true);

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
//        pause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
//        stop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
//        release();
    }

}
