package com.roydon.niuyin.http.server;

import com.hjq.http.model.BodyType;

/**
 * @description: 测试环境
 */
public class TestServer extends ReleaseServer {

    /**
     * http://8.130.182.227:9090
     */
    private static String SERVER_HOST_TEST = "https://9cdec19.r18.cpolar.top";

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