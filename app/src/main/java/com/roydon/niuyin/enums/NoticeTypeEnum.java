package com.roydon.niuyin.enums;

/**
 * 消息通知类型枚举
 */
public enum NoticeTypeEnum {

    LIKE("0", "点赞"),
    FAVORITE("1", "收藏"),
    FOLLOW("2", "关注"),
    REPLAY("3", "回复评论"),
    LIKE_COMMENT("4", "赞了评论"),
    ;

    private final String code;
    private final String info;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    NoticeTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static NoticeTypeEnum findByCode(String code) {
        for (NoticeTypeEnum value : NoticeTypeEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }

}
