package com.roydon.niuyin.http.server;

import com.hjq.http.config.IRequestServer;

/**
 * desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {

    @Override
    public String getHost() {
        return "https://106.14.105.101:8088/";
    }

    @Override
    public String getPath() {
        return "api/";
    }
}