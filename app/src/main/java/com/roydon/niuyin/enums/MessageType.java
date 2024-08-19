package com.roydon.niuyin.enums;

import com.roydon.niuyin.R;

/**
 * @author roydon
 * @date 2024/2/11 17:43
 * @description 消息类型
 */
public enum MessageType {

    FRIEND("0", "新朋友", R.drawable.notice_friend),
    BEHAVE("1", "互动通知", R.drawable.notice_behave),
    SYSTEM("2", "系统通知", R.drawable.notice_system),
    ACTIVITY("3", "活动通知", R.drawable.notice_activity),
    SERVICE("4", "服务通知", R.drawable.notice_service),
    ASSIGNMENT("5", "任务通知", R.drawable.notice_assignment),
    FUN("6", "功能通知", R.drawable.notice_fun),
    ;

    private final String code;
    private final String info;
    private final int icon;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }


    public int getIcon() {
        return icon;
    }

    MessageType(String code, String info, int icon) {
        this.code = code;
        this.info = info;
        this.icon = icon;
    }

    public static MessageType findByCode(String code) {
        for (MessageType value : MessageType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }

}
