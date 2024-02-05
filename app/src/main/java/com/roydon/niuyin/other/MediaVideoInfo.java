package com.roydon.niuyin.other;

/**
 * 视频详情
 *
 * @AUTHOR: roydon
 * @DATE: 2024/2/4
 **/
public class MediaVideoInfo {
    private String format = null;
    private long duration = -1L;
    private String decoder;
    private int bitRate = -1;
    private float frameRate = -1.0F;
    private Integer width;
    private Integer height;

    public MediaVideoInfo(String format, long duration, String decoder, int bitRate, float frameRate, Integer width, Integer height) {
        this.format = format;
        this.duration = duration;
        this.decoder = decoder;
        this.bitRate = bitRate;
        this.frameRate = frameRate;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaVideoInfo{" +
                "format='" + format + '\'' +
                ", duration=" + duration +
                ", decoder='" + decoder + '\'' +
                ", bitRate=" + bitRate +
                ", frameRate=" + frameRate +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDecoder() {
        return decoder;
    }

    public void setDecoder(String decoder) {
        this.decoder = decoder;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}