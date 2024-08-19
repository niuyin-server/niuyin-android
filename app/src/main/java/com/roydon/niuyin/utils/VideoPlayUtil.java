package com.roydon.niuyin.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.roydon.niuyin.enums.PublishType;
import com.roydon.niuyin.enums.VideoScreenType;
import com.roydon.niuyin.other.MediaVideoInfo;
import com.roydon.niuyin.ui.activity.VideoImagePlayActivity;
import com.roydon.niuyin.ui.activity.VideoPlayActivity;

import java.util.Objects;

public class VideoPlayUtil {

    public static void toPlayVideoActivity(Context context, String videoId, String videoInfo, String publishType) {
        if (publishType.equals(PublishType.VIDEO.getCode())) {
            // 视频
            // 设置视频比例 4:3竖屏视频
            if (!Objects.isNull(videoInfo)) {
                MediaVideoInfo mediaVideoInfo = new Gson().fromJson(videoInfo, MediaVideoInfo.class);
                if (mediaVideoInfo.getWidth() > mediaVideoInfo.getHeight()) {
                    // 横屏 1.6
                    VideoPlayActivity.start(context, videoId, VideoScreenType.HENG.getCode());
                } else if (mediaVideoInfo.getHeight() > mediaVideoInfo.getWidth()) {
                    // 竖屏 0.75
                    VideoPlayActivity.start(context, videoId, VideoScreenType.SHU.getCode());
                } else if (Objects.equals(mediaVideoInfo.getWidth(), mediaVideoInfo.getHeight())) {
                    // 正方形视频
                    VideoPlayActivity.start(context, videoId, VideoScreenType.SQUARE.getCode());
                }
            } else {
                // 默认横屏
                VideoPlayActivity.start(context, videoId, VideoScreenType.DEFAULT.getCode());
            }
        } else if (publishType.equals(PublishType.IMAGE.getCode())) {
            // 图文
            VideoImagePlayActivity.start(context, videoId);
        }
    }
}
