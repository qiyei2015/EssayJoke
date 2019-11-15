package com.qiyei.performance.bootstarter.task;

import android.os.Looper;
import android.os.Process;

import androidx.core.os.TraceCompat;

import com.qiyei.performance.bootstarter.TaskDispatcher;
import com.qiyei.performance.bootstarter.stat.TaskStat;
import com.qiyei.performance.bootstarter.utils.Logger;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DispatchRunnable implements Runnable {

    private static final String TAG = "DispatchRunnable";

    /**
     * 持有的Task
     */
    private Task mTask;
    /**
     * Task分发器
     */
    private TaskDispatcher mTaskDispatcher;

    public DispatchRunnable(Task mTask) {
        this.mTask = mTask;
    }

    public DispatchRunnable(Task mTask, TaskDispatcher mTaskDispatcher) {
        this.mTask = mTask;
        this.mTaskDispatcher = mTaskDispatcher;
    }

    @Override
    public void run() {
        TraceCompat.beginSection(mTask.getName());
        // TODO: 2019/11/13
        Logger.d(TAG,mTask.getName() + " begin run situation ");

        //1 设置优先级
        Process.setThreadPriority(mTask.priority());

        //2 等待依赖执行完毕
        long startTime = System.currentTimeMillis();
        mTask.setState(State.WAITING);
        mTask.waitForDepends();
        long waitTime = System.currentTimeMillis() - startTime;

        //3 执行Task
        startTime = System.currentTimeMillis();
        mTask.setState(State.RUNNING);
        mTask.run();

        //4 执行尾部的任务
        Runnable tailTask = mTask.getTailRunnable();
        if (tailTask != null){
            tailTask.run();
        }

        //5 不在主线程的task 标记Task，并且分配下一个
        if (!mTask.needCall() || !mTask.runOnMainThread()){
            printTaskLog(startTime,waitTime);

            TaskStat.markTaskDone();

            mTask.setState(State.FINISHED);
            if (mTaskDispatcher != null){
                mTaskDispatcher.satisfyChildren(mTask);
                mTaskDispatcher.markTaskDone(mTask);
            }
            Logger.d(TAG,mTask.getName() + " finish");
        }

        TraceCompat.endSection();
    }

    /**
     * 打印出来Task执行的日志
     *
     * @param startTime
     * @param waitTime
     */
    private void printTaskLog(long startTime, long waitTime) {
        long runTime = System.currentTimeMillis() - startTime;
//        Logger.d(mTask.getName() + "  wait " + waitTime + "    run "
//                + runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper())
//                + "  needWait " + (mTask.needWait() || (Looper.getMainLooper() == Looper.myLooper()))
//                + "  ThreadId " + Thread.currentThread().getId()
//                + "  ThreadName " + Thread.currentThread().getName()
//                + "  Situation  " + TaskStat.getCurrentSituation()
//        );
        Logger.d(TAG,mTask.getName() + "  wait " + waitTime + "    run "
                + runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper())
                + "  needWait " + (mTask.needWait() || (Looper.getMainLooper() == Looper.myLooper()))
                + "  ThreadId " + Thread.currentThread().getId()
                + "  ThreadName " + Thread.currentThread().getName()
        );
    }
}
