package com.qiyei.performance.bootstarter.task;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 主线程的Task
 */
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
