package com.roydon.niuyin.action;

/**
 *    time   : 2019/12/08
 *    desc   : 侧滑意图
 */
public interface SwipeAction {

    /**
     * 是否使用侧滑
     */
    default boolean isSwipeEnable() {
        // 默认开启
        return true;
    }
}