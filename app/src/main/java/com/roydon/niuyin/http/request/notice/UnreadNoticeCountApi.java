package com.roydon.niuyin.http.request.notice;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/11 17:06
 * @description niuyin-android
 * 未读消息数
 * get
 */
public class UnreadNoticeCountApi implements IRequestApi {

    @Override
    public String getApi() {
        return "notice/api/v1/app/unreadCount";
    }

}
