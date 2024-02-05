package com.roydon.niuyin.enums;

/**
 * @author roydon
 * @date 2024/2/4 18:16
 * @description niuyin-android
 */
public enum VideoScreenType {

    DEFAULT("-1", "默认"),
    HENG("0", "横屏"),
    SHU("1", "竖屏"),
    ;

    private final String code;
    private final String info;

    VideoScreenType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static VideoScreenType findByCode(String code) {
        for (VideoScreenType value : VideoScreenType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
