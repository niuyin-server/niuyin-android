package com.roydon.niuyin.domain;

import com.roydon.niuyin.enums.NoticeBehaveShowType;

/**
 * @author roydon
 * @date 2024/2/11 21:09
 * @description niuyin-android
 */
public class NoticeBehaveShowTypeBean {
    private NoticeBehaveShowType noticeBehaveShowType;

    public NoticeBehaveShowTypeBean(NoticeBehaveShowType noticeBehaveShowType) {
        this.noticeBehaveShowType = noticeBehaveShowType;
    }

    public NoticeBehaveShowType getNoticeBehaveType() {
        return noticeBehaveShowType;
    }

    public void setNoticeBehaveType(NoticeBehaveShowType noticeBehaveShowType) {
        this.noticeBehaveShowType = noticeBehaveShowType;
    }
}
