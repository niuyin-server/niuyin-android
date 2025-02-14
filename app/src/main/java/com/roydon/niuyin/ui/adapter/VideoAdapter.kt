package com.roydon.niuyin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.roydon.niuyin.databinding.ItemVideoBinding
import com.roydon.niuyin.helper.player.VideoPlayer
import com.roydon.niuyin.http.response.video.VideoRecommendVO
import com.roydon.niuyin.widget.LikeView

class VideoAdapter(val context: Context, val recyclerView: RecyclerView) :
    BaseAdapter<VideoAdapter.VideoViewHolder, VideoRecommendVO>(VideoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        mList[position]?.let {
            holder.binding.controller.setVideoData(it)
            Glide.with(context)
                .asBitmap()
                .load(it.videoUrl)
                .centerCrop()
                .apply(RequestOptions.frameOf(0))  // 从第一帧开始
                .into(holder.binding.ivCover)
            holder?.binding?.likeView?.setOnLikeListener(object : LikeView.OnLikeListener {
                override fun onLikeListener() {
//                    if (!it.isLiked) {  //未点赞，会有点赞效果，否则无
//                        holder?.binding?.controller!!.like()
//                    }
                }
            })
            holder.binding.ivPlay.alpha = 0.5f
        }
        //利用预加item，提前加载缓存资源
        mList[position].mediaSource = buildMediaSource(mList[position].videoUrl)
    }

    /**
     * 构建一个共用缓存文件
     */
    val cache: SimpleCache by lazy {
        //构建缓存文件
        val cacheFile = context.cacheDir.resolve("niuyin_cache_file$this.hashCode()")
        //构建simpleCache缓存实例
        SimpleCache(
            cacheFile,
            LeastRecentlyUsedCacheEvictor(VideoPlayer.MAX_CACHE_BYTE),
            StandaloneDatabaseProvider(context)
        )
    }

    /**
     * 构建当前url视频的缓存
     */
    private fun buildMediaSource(url: String): ProgressiveMediaSource {
        //开启缓存文件
        val mediaItem = MediaItem.fromUri(url)
        //构建 DataSourceFactory
        val dataSourceFactory = CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(DefaultDataSource.Factory(context))
        //构建 MediaSource
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    /**
     * 通过position获取当前item.rootview
     */
    fun getRootViewAt(position: Int): View? {
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
        return if (viewHolder != null && viewHolder is VideoViewHolder) {
            viewHolder.itemView
        } else {
            null
        }
    }

    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}

class VideoDiff : DiffUtil.ItemCallback<VideoRecommendVO>() {
    override fun areItemsTheSame(oldItem: VideoRecommendVO, newItem: VideoRecommendVO): Boolean {
        return (oldItem.author!!.userId == newItem.author!!.userId)
    }

    override fun areContentsTheSame(oldItem: VideoRecommendVO, newItem: VideoRecommendVO): Boolean {
        return (oldItem.videoUrl == newItem.videoUrl && oldItem.author!!.userId == newItem.author!!.userId)
    }

}