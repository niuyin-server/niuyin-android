package com.roydon.niuyin.http.model;

import java.io.Serializable;
import java.util.List;

public class PageDataInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 总记录数
     */
    private long total;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<T> getRows() {
        return rows;
    }

    public long getTotal() {
        return total;
    }
}
