package com.qiyei.sdk.launchstarter;

import android.os.Looper;


import androidx.annotation.UiThread;

import com.qiyei.sdk.launchstarter.task.ITask;
import com.qiyei.sdk.launchstarter.task.Task;
import com.qiyei.sdk.launchstarter.task.TaskRunnable;
import com.qiyei.sdk.launchstarter.task.TaskStatus;
import com.qiyei.sdk.launchstarter.task.TaskUtil;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.binder.ComputeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by qiyei2015 on 2019/4/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class LauncherManager {

    private static final String TAG = "LauncherManager";
    private static volatile LauncherManager sInstance = null;
    private long mCostTime = 0;

    /**
     * 等待超时
     */
    private static final int TIME_OUT = 5 * 1000;

    /**
     * K 某个task V:依赖的task集合
     */
    private Map<Class<? extends Task>, List<Task>> mDependTaskMap;
    /**
     * 已经结束的task
     */
    private List<Class<? extends Task>> mFinishTaskList;
    /**
     * 需要等待的task
     */
    private List<Task> mNeedWaitTaskList;
    /**
     * 所有task
     */
    private List<Task> mAllTaskList;

    /**
     * 所有task
     */
    private List<Task> mMainThreadTaskList;

    /**
     * 所有taskClass
     */
    private List<Class<? extends Task>> mAllTaskClazzList;
    /**
     * 主线程等待子线程的计数
     */
    private CountDownLatch mCountDownLatch;

    private List<Future> mFutureList;

    private LauncherManager(){
        mDependTaskMap = new HashMap<>();
        mFinishTaskList = new ArrayList<>();
        mNeedWaitTaskList = new ArrayList<>();
        mAllTaskList = new ArrayList<>();
        mAllTaskClazzList = new ArrayList<>();
        mMainThreadTaskList = new ArrayList<>();
        mCountDownLatch = new CountDownLatch(0);
        mFinishTaskList = new ArrayList<>();

    }


    public static LauncherManager getDefault(){
        if (sInstance == null){
            synchronized (LauncherManager.class){
                if (sInstance == null){
                    sInstance = new LauncherManager();
                }
            }
        }
        return sInstance;
    }


    /**
     * 添加task
     * @param task
     * @return
     */
    public LauncherManager addTask(Task task){
        if (task != null){
            collectDepend(task);
            mAllTaskList.add(task);
            mAllTaskClazzList.add(task.getClass());
            if (needWait(task)){
                mNeedWaitTaskList.add(task);
            }
            if (task.isMainThread()){
                mMainThreadTaskList.add(task);
            }
        }

        return this;
    }

    @UiThread
    public void start(){
        mCostTime = System.currentTimeMillis();
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must be called from UiThread");
        }
        if (mAllTaskList.size() > 0){
            mAllTaskList = TaskUtil.sort(mAllTaskList,mAllTaskClazzList);
            mCountDownLatch = new CountDownLatch(mNeedWaitTaskList.size());

            dispatchAsyncTask();
            dispatchMainTask();
        }
    }

    public void await(){
        try {
            mCountDownLatch.await(TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void end(String tag){
        long cost = (System.currentTimeMillis() - mCostTime);
        LogManager.d(TAG, "app start " + tag + " cost:" + cost + " ms");
    }

    private boolean needWait(Task task) {
        return !task.isMainThread() && task.needWait();
    }

    private void collectDepend(Task task) {
        if (task.getDepends() != null && task.getDepends().size() > 0){
            for (Class<? extends Task> clazz: task.getDepends()){
                if (mDependTaskMap.get(clazz) == null){
                    mDependTaskMap.put(clazz,new ArrayList<>());
                }
                mDependTaskMap.get(clazz).add(task);
                if (mFinishTaskList.contains(task)){
                    task.countDown();
                }
            }
        }

    }

    private void dispatchMainTask() {
        for (Task task : mMainThreadTaskList){
            new TaskRunnable(task,this).run();
        }
    }

    private void sendTaskReal(Task task) {
        Future future = task.getExecutor().submit(new TaskRunnable(task,this));
        mFutureList.add(future);
    }

    private void markTaskDone(Task task) {
        if (needWait(task)){
            mFinishTaskList.add(task.getClass());
            mNeedWaitTaskList.remove(task);
            //计数减1
            mCountDownLatch.countDown();
        }
    }

    private void dispatchAsyncTask() {
        for (Task task : mAllTaskList){
            if (task.isMainThread()){
                markTaskDone(task);
            }else {
                sendTaskReal(task);
            }
            task.setStatus(TaskStatus.DISPATCH);
        }
    }

}
