package com.qiyei.performance.bootstarter.task;

/**
 * @author Created by qiyei2015 on 2019/11/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public enum State {
    //初始状态
    INITIAL,
    //等待状态
    WAITING,
    //分发状态
    DISPATCH,
    //运行状态
    RUNNING,
    //结束状态
    FINISHED
}
