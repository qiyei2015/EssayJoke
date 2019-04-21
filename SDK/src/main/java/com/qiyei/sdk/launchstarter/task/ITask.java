package com.qiyei.sdk.launchstarter.task;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Created by qiyei2015 on 2019/4/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface ITask {

    /**
     * 名字
     * @return
     */
    String getName();

    /**
     * 获取状态
     * @return
     */
    TaskStatus getStatus();

    /**
     * 所依赖的
     * @return
     */
    List<Class<? extends Task>> getDepends();

    /**
     * 依赖计数减1
     * @return
     */
    int countDown();

    /**
     * 需要等待
     * @return
     */
    boolean needWait();

    /**
     * 是否主线程
     * @return
     */
    boolean isMainThread();

    /**
     * 获取执行对应的线程池
     * @return
     */
    ExecutorService getExecutor();

    /**
     * 线程优先级
     * @return
     */
    int getThreadPriority();
}
