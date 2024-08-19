package com.roydon.niuyin.enums;

public enum NoticeType {

    LIKE("0", "点赞"),
    FOLLOW("1", "关注"),
    FAVORITE("2", "收藏"),
    COMMENT_ADD("3", "视频被评论"),
    COMMENT_REPLAY("4", "回复评论"),
    COMMENT_LIKE("5", "赞了评论"),
    ;
    private final String code;
    private final String info;

    NoticeType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static NoticeType findByCode(String code) {
        for (NoticeType value : NoticeType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
