package com.roydon.niuyin.helper.player

import com.google.android.exoplayer2.ExoPlayer

interface IPlayer {

    fun playVideo(url: String)

    fun getPlayer(): ExoPlayer

    fun play()

    fun pause()

    fun stop()

    fun release()

    fun isPlaying(): Boolean
}