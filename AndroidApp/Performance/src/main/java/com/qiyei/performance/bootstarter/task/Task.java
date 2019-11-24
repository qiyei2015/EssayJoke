package com.qiyei.performance.bootstarter.task;

import android.os.Process;

import com.qiyei.performance.bootstarter.utils.ThreadPoolExecutorUtils;
import com.qiyei.sdk.log.LogManager;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: task主要实现类
 */
public abstract class Task implements ITask {

    private static final String TAG = "TASK";

    /**
     * 是否在主进程，用于多进程场景
     */
    protected boolean isMainProcess = true;
    /**
     * 依赖的task
     */
    private CountDownLatch mDepends = new CountDownLatch(dependsOn() == null ? 0 : dependsOn().size());

    /**
     * 任务名
     */
    private String mName;
    /**
     * 任务id
     */
    private String mTaskId;

    /**
     * 任务状态
     */
    private State mState;

    private Callable mCallable;

    private Runnable mRunnable;

    private int mPriority = Process.THREAD_PRIORITY_BACKGROUND;

    /**
     * @return {@link #mName}
     */
    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void run() {
        LogManager.i(TAG,getInfo() + " start running");
        if (mRunnable != null){
            mRunnable.run();
            LogManager.i(TAG,getInfo() + " end running");
        } else if (mCallable != null){
            try {
                Object o = mCallable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int priority() {
        return mPriority;
    }

    /**
     * Task执行在哪个线程池，默认在IO的线程池；
     * CPU 密集型的一定要切换到DispatcherExecutor.getCPUExecutor();
     * @return
     */
    @Override
    public ExecutorService runOn() {
        return ThreadPoolExecutorUtils.getIOExecutor();
    }

    /**
     * 默认无依赖
     * @return
     */
    @Override
    public List<Class<? extends Task>> dependsOn() {
        return null;
    }

    /**
     * 默认无依赖
     * @return
     */

    public List<Task> dependsOnTask() {
        return null;
    }

    @Override
    public boolean needWait() {
        return false;
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }

    /**
     * 默认只在主进程执行
     * @return
     */
    @Override
    public boolean onlyInMainProcess() {
        return true;
    }

    @Override
    public Runnable getTailRunnable() {
        return null;
    }

    @Override
    public void setTaskCallBack(ITaskCallBack callBack) {

    }

    @Override
    public boolean needCall() {
        return false;
    }

    /**
     * 当前Task等待，让依赖的Task先执行
     */
    public void waitForDepends(){
        try {
            mDepends.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依赖的Task执行完一个,计数器减一
     */
    public void countDownForDepends(){
        mDepends.countDown();
    }

    /**
     * 是否需要尽快执行，解决特殊场景的问题：
     * 一个Task耗时非常多但是优先级却一般，很有可能开始的时间较晚，
     * 导致最后只是在等它，这种可以早开始。
     * @return
     */
    public boolean needRunAsSoon(){
        return false;
    }


    /**
     * @return {@link #mState}
     */
    public State getState() {
        return mState;
    }

    /**
     * @param state the {@link #mState} to set
     */
    public void setState(State state) {
        mState = state;
    }

    /**
     * @return {@link #mTaskId}
     */
    public String getTaskId() {
        return mTaskId;
    }

    /**
     * @return {@link #mCallable}
     */
    public Callable getTask() {
        return mCallable;
    }

    /**
     * @return {@link #mRunnable}
     */
    public Runnable getRunnable() {
        return mRunnable;
    }

    public String getInfo(){
        return TAG + " --> name=" + mName + ",id=" + mTaskId;
    }

    public static class Builder{
        Task task;

        public Builder() {
            task = new Task() {
                @Override
                public void run() {

                }
            };
            task.mTaskId = TAG + "_" + UUID.randomUUID().toString();

        }

        /**
         * @param name the {@link #mName} to set
         */
        public Builder setName(String name) {
            task.mName = name;
            return this;
        }

        public Builder setTaskCallBack(ITaskCallBack callBack) {
            task.setTaskCallBack(callBack);
            return this;
        }

        /**
         * @param callable the {@link #mCallable} to set
         */
        public Builder setTask(Callable callable) {
            task.mCallable = callable;
            return this;
        }

        /**
         * @param runnable the {@link #mCallable} to set
         */
        public Builder setTask(Runnable runnable) {
            task.mRunnable = runnable;
            return this;
        }

        public Task build(){
            LogManager.i(TAG,task.getInfo());
            return task;
        }
    }

}
