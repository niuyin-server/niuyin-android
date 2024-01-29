package com.roydon.niuyin.http.model;

/**
 *    desc   : 统一接口数据结构
 */
public class HttpData<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public T getData() {
        return data;
    }
}