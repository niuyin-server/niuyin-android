package com.roydon.niuyin.helper.player

import android.content.Context
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object VideoCacheManager {

    // 私有化缓存实例
    @Volatile
    private var simpleCache: SimpleCache? = null

    // 构建缓存文件的文件夹
    private var cacheFile: File? = null

    // 获取 SimpleCache 的实例（单例模式）
    fun getInstance(context: Context): SimpleCache {
        return simpleCache ?: synchronized(this) {
            simpleCache ?: buildSimpleCache(context).also { simpleCache = it }
        }
    }

    // 构建 SimpleCache 的具体实现
    private fun buildSimpleCache(context: Context): SimpleCache {
        // 构建缓存文件
        cacheFile = context.cacheDir.resolve("niuyin_cache_file_${this.hashCode()}")
        // 构建 SimpleCache 缓存实例
        return SimpleCache(
            cacheFile!!,
            LeastRecentlyUsedCacheEvictor(VideoPlayer.MAX_CACHE_BYTE),
            StandaloneDatabaseProvider(context)
        )
    }

    // 清理缓存（可选）
    fun releaseCache() {
        simpleCache?.release()
        simpleCache = null
    }
}
