package com.roydon.niuyin.helper.player;

import android.content.Context;

import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

/**
 * @author roydon
 * @date 2025/2/14 17:18
 * @description niuyin-android
 */
public class VideoCacheMainManager {
    // 私有静态变量（单例实例）
    private static volatile SimpleCache cache;

    // 缓存文件目录
    private static File cacheFile;

    // 最大缓存大小
    private static final long MAX_CACHE_BYTE = 200 * 1024 * 1024; // 100MB

    // 私有构造函数，防止外部实例化
    private VideoCacheMainManager() {
    }

    // 获取 SimpleCache 的单例实例
    public static SimpleCache getInstance(Context context) {
        if (cache == null) { // 第一次检查
            synchronized (VideoCacheManager.class) {
                if (cache == null) { // 第二次检查
                    // 创建缓存文件目录
                    cacheFile = new File(context.getCacheDir(), "niuyin_cache_file");
                    // 创建 SimpleCache 实例
                    cache = new SimpleCache(
                            cacheFile,
                            new LeastRecentlyUsedCacheEvictor(MAX_CACHE_BYTE),
                            new StandaloneDatabaseProvider(context)
                    );
                }
            }
        }
        return cache;
    }

    // 释放缓存资源（可选）
    public static void releaseCache() {
        if (cache != null) {
            cache.release();
            cache = null;
        }
    }
}
