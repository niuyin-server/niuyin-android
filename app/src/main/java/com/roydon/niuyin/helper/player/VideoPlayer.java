package com.roydon.niuyin.helper.player;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.roydon.niuyin.databinding.ViewPlayviewBinding;

import java.io.File;

public class VideoPlayer extends FrameLayout implements IPlayer, DefaultLifecycleObserver {

    private TrackSelector trackSelector;

    private SimpleExoPlayer mPlayer;

    public static final long MAX_CACHE_BYTE = 1024 * 1024 * 200; // 200MB

    private final DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(true) // 缓冲时时间优先级高于大小
            .setBufferDurationsMs(5000, 7000, 700, 1000) // 设置缓冲时间
            .build();

    private SimpleCache cache;

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPlayer(context);
        File cacheFile = new File(context.getCacheDir(), "niuyin_cache_file");
        cache = new SimpleCache(cacheFile, new LeastRecentlyUsedCacheEvictor(MAX_CACHE_BYTE), new StandaloneDatabaseProvider(context));
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        pause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        stop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        release();
    }

    private void initPlayer(Context context) {
        trackSelector = new DefaultTrackSelector(context);
        mPlayer = new SimpleExoPlayer.Builder(context)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build();
        ViewPlayviewBinding binding = ViewPlayviewBinding.inflate(LayoutInflater.from(context), this, true);
        binding.playerView.setPlayer(mPlayer);
        binding.playerView.setUseController(false);
        mPlayer.setPlayWhenReady(true);
        mPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
    }

    public void playVideo(BaseMediaSource mediaSource) {
        mPlayer.setMediaSource(mediaSource);
        mPlayer.prepare();
        mPlayer.play();
    }

    @Override
    public void playVideo(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        MediaItem mediaItem = MediaItem.fromUri(url);
        CacheDataSource.Factory dataSourceFactory = new CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(new DefaultDataSource.Factory(getContext()));
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem);
        mPlayer.setMediaSource(mediaSource);
        mPlayer.prepare();
        mPlayer.play();
    }

    @Override
    public SimpleExoPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void play() {
        mPlayer.play();
    }

    @Override
    public void stop() {
        mPlayer.stop();
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }
}
