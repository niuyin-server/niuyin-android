package com.roydon.niuyin.http.server;

import com.hjq.http.model.BodyType;

/**
 * @description: 测试环境
 */
public class TestServer extends ReleaseServer {

    /**
     * http://39.101.67.45:9090
     * https://6da51c2a.r18.cpolar.top
     */
    @Override
    public String getHost() {
        return "https://bbd639.r2.cpolar.top/";
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}