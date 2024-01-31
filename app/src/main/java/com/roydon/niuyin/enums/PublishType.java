package com.roydon.niuyin.enums;

public enum PublishType {

    VIDEO("0", "视频"),
    IMAGE("1", "图文"),
    ;

    private final String code;
    private final String info;

    PublishType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static PublishType findByCode(String code) {
        for (PublishType value : PublishType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
