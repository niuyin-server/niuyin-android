package com.roydon.niuyin.http.server;

import com.hjq.http.model.BodyType;

/**
 * @description: 测试环境
 */
public class TestServer extends ReleaseServer {

    /**
     * http://43.240.221.8:9090
     */
    private static String SERVER_HOST_TEST = "http://43.240.221.8:9090";

    @Override
    public String getHost() {
        return SERVER_HOST_TEST;
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