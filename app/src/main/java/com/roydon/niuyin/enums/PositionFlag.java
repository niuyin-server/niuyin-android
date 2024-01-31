package com.roydon.niuyin.enums;

public enum PositionFlag {

    DISABLE("0", "禁用定位"),
    OPEN("1", "开启定位"),
    ;

    private final String code;
    private final String info;

    PositionFlag(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static PositionFlag findByCode(String code) {
        for (PositionFlag value : PositionFlag.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
