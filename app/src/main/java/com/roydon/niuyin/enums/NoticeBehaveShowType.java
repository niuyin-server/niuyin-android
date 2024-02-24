package com.roydon.niuyin.enums;

import com.roydon.niuyin.R;

public enum NoticeBehaveShowType {

    All("", "全部消息", R.drawable.notice_behave),
    LIKE("0", "点赞", R.drawable.notice_like),
    FOLLOW("1", "关注", R.drawable.notice_follow),
    FAVORITE("2", "收藏", R.drawable.notice_favorite),
    COMMENT_ADD("3", "视频被评论", R.drawable.notice_commend),
    COMMENT_REPLAY("4", "回复评论", R.drawable.notice_commend),
    COMMENT_LIKE("5", "赞了评论", R.drawable.notice_commend),
    ;

    private final String code;
    private final String info;
    private final int icon;

    NoticeBehaveShowType(String code, String info, int icon) {
        this.code = code;
        this.info = info;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static NoticeBehaveShowType findByCode(String code) {
        for (NoticeBehaveShowType value : NoticeBehaveShowType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
