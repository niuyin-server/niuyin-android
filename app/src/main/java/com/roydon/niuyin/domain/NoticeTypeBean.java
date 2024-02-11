package com.roydon.niuyin.domain;

import com.roydon.niuyin.enums.MessageType;

/**
 * @author roydon
 * @date 2024/2/11 17:40
 * @description niuyin-android
 */
public class NoticeTypeBean {

    private MessageType messageType;
    private int icon;
    private String name;
    private String desc;

    public NoticeTypeBean(MessageType messageType, int icon, String name, String desc) {
        this.messageType = messageType;
        this.icon = icon;
        this.name = name;
        this.desc = desc;
    }

    public NoticeTypeBean(MessageType messageType, String desc) {
        this.messageType = messageType;
        this.icon = messageType.getIcon();
        this.name = messageType.getInfo();
        this.desc = desc;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
