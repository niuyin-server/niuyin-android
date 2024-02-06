package com.roydon.niuyin.http.server;

import com.hjq.http.config.IRequestServer;

/**
 * desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {

    @Override
    public String getHost() {
        return "http://39.101.67.45:9090/";
    }

    @Override
    public String getPath() {
        return "api/";
    }
}