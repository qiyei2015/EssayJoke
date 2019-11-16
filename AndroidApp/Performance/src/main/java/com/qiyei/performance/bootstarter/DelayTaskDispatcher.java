package com.qiyei.performance.bootstarter;

import android.os.Looper;
import android.os.MessageQueue;

import com.qiyei.performance.bootstarter.task.DispatchRunnable;
import com.qiyei.performance.bootstarter.task.Task;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Created by qiyei2015 on 2019/11/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DelayTaskDispatcher {

    private static final String TAG = "DelayTaskDispatcher";
    /**
     * 任务队列
     */
    private Queue<Task> mTaskQueue = new LinkedBlockingDeque<>();
    /**
     * IdleHandler
     */
    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            Task task = mTaskQueue.poll();
            new DispatchRunnable(task).run();
            return !mTaskQueue.isEmpty();
        }
    };

    public DelayTaskDispatcher addTask(Task task){
        mTaskQueue.add(task);
        return this;
    }

    public void start(){
        Looper.getMainLooper().getQueue().addIdleHandler(mIdleHandler);
    }
}
