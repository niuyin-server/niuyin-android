package com.roydon.niuyin.http.response.entity;

import java.io.Serializable;

public class VideoPosition implements Serializable {
    private static final long serialVersionUID = 649827621807953953L;
    /**
     * 位置id
     */
    private Long positionId;
    /**
     * 视频id
     */
    private String videoId;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 城市code
     */
    private String cityCode;
    /**
     * 区
     */
    private String district;
    /**
     * 街道
     */
    private String township;
    /**
     * 邮编
     */
    private String adcode;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态标志（0：启用、1：禁用）
     */
    private String status;


}
