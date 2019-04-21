package com.qiyei.sdk.launchstarter.task;

import android.os.Process;
import android.support.v4.os.TraceCompat;

import com.qiyei.sdk.launchstarter.LauncherManager;

/**
 * @author Created by qiyei2015 on 2019/4/14.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class TaskRunnable implements Runnable {
    private Task mTask;
    private LauncherManager mLauncherManager;

    public TaskRunnable(Task task, LauncherManager launcherManager) {
        mTask = task;
        mLauncherManager = launcherManager;
    }

    @Override
    public void run() {
        TraceCompat.beginSection(mTask.getName());
        //设置线程优先级
        Process.setThreadPriority(mTask.getThreadPriority());

        mTask.setStatus(TaskStatus.WAITING);
        mTask.countDown();

    }


}
