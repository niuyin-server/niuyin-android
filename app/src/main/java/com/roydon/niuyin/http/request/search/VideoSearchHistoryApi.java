package com.roydon.niuyin.http.request.search;

import com.hjq.http.config.IRequestApi;

/**
 * @author roydon
 * @date 2024/2/3 22:00
 * @description 搜索记录
 * get
 */
public class VideoSearchHistoryApi implements IRequestApi {

    @Override
    public String getApi() {
        return "search/api/v1/app/history/load";
    }
}
