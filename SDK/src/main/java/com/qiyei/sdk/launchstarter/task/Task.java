package com.qiyei.sdk.launchstarter.task;

import android.os.Process;

import com.qiyei.sdk.executor.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author Created by qiyei2015 on 2019/4/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public abstract class Task implements ITask{

    /**
     * 名字
     */
    private String mName;
    /**
     * 是否主线程
     */
    private boolean isMainThread;
    /**
     * 依赖同步计数
     */
    private CountDownLatch mCountDownLatch;
    /**
     * 状态
     */
    private TaskStatus mStatus;

    public Task(String name, boolean isMainThread) {
        mName = name;
        this.isMainThread = isMainThread;
        List<Class<? extends Task>> list = getDepends();
        mCountDownLatch = new CountDownLatch(list == null ? 0 : list.size());
        mStatus = TaskStatus.INITIAL;
    }

    @Override
    public String getName() {
        return mName;
    }

    /**
     * @param status the {@link #mStatus} to set
     */
    public void setStatus(TaskStatus status) {
        mStatus = status;
    }

    /**
     * @return {@link #mStatus}
     */
    @Override
    public TaskStatus getStatus() {
        return mStatus;
    }

    @Override
    public int countDown() {
        mCountDownLatch.countDown();
        return (int)mCountDownLatch.getCount();
    }

    /**
     * @return {@link #mCountDownLatch}
     */
    public int getCountDownLatchCount() {
        return (int) mCountDownLatch.getCount();
    }

    /**
     * @return {@link #isMainThread}
     */
    @Override
    public boolean isMainThread() {
        return isMainThread;
    }

    @Override
    public ExecutorService getExecutor() {
        return ExecutorManager.getIOExecutor();
    }

    @Override
    public int getThreadPriority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }
}
