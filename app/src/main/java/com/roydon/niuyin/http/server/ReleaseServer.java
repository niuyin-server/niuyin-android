package com.roydon.niuyin.http.server;

import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.BodyType;

/**
 * desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {

    private static String SERVER_HOST_RELEASE = "http://8.130.182.227:9090";

    @Override
    public String getHost() {
        return SERVER_HOST_RELEASE;
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}