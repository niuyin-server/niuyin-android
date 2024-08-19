package com.roydon.niuyin.enums;

/**
 * 视频评论分页排序枚举
 *
 * @AUTHOR: roydon
 * @DATE: 2024/4/4
 **/
public enum VideoCommentPageOrderEnum {

    CREATE_TIME("0", "create_time", "最新评论"),
    LIKE_NUM("1", "like_num", "热门评论"),
    ;

    private final String code;
    private final String info;
    private final String desc;

    VideoCommentPageOrderEnum(String code, String info, String desc) {
        this.code = code;
        this.info = info;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public String getDesc() {
        return desc;
    }

    public static VideoCommentPageOrderEnum findByCode(String code) {
        for (VideoCommentPageOrderEnum value : VideoCommentPageOrderEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }

}
