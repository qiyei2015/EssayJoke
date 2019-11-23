package com.qiyei.performance.bootstarter;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.UiThread;

import com.qiyei.performance.bootstarter.stat.TaskStat;
import com.qiyei.performance.bootstarter.task.DispatchRunnable;
import com.qiyei.performance.bootstarter.task.ITaskCallBack;
import com.qiyei.performance.bootstarter.task.State;
import com.qiyei.performance.bootstarter.task.Task;
import com.qiyei.performance.bootstarter.utils.Logger;
import com.qiyei.performance.bootstarter.sort.TaskSort;
import com.qiyei.performance.bootstarter.utils.ProcessUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: TaskDispatcher
 */
public class TaskDispatcher {

    private static final String TAG = "TaskDispatcher";

    private long mStartTime;

    private static final int WAIT_TIME = 10000;

    private static Context sContext;
    /**
     * 是否初始化
     */
    private static volatile boolean sHasInit;

    private static boolean sIsMainProcess;

    private List<Future> mFutures = new ArrayList<>();
    private List<Task> mAllTasks = new ArrayList<>();
    private List<Class<? extends Task>> mClsAllTasks = new ArrayList<>();

    private volatile List<Task> mMainThreadTasks = new ArrayList<>();

    private CountDownLatch mCountDownLatch;

    /**
     * 保存需要Wait的Task的数量
     */
    private AtomicInteger mNeedWaitCount = new AtomicInteger();
    /**
     * 调用了await的时候还没结束的且需要等待的Task
     */
    private List<Task> mNeedWaitTasks = new ArrayList<>();
    /**
     * 已经结束的Task
     */
    private volatile List<Class<? extends Task>> mFinishedTasks = new ArrayList<>();

    private HashMap<Class<? extends Task>,ArrayList<Task>> mDependedHashMap = new HashMap<>();
    /**
     * 启动器分析的次数，统计下分析的耗时
     */
    private AtomicInteger mAnaylyseCount = new AtomicInteger();


    private TaskDispatcher() {

    }

    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        if (context != null){
            sContext = context.getApplicationContext();
            sHasInit = true;
            sIsMainProcess = ProcessUtils.isMainProcss(context);
        }
    }

    public static TaskDispatcher getInstance(){
        if (!sHasInit){
            throw new RuntimeException("must call TaskDispatcher.init first");
        }
        return new TaskDispatcher();
    }

    public TaskDispatcher addTask(Task task){
        if (task != null){
            collectDepends(task);
            mAllTasks.add(task);
            mClsAllTasks.add(task.getClass());
            //非主线程且需要wait的，主线程不需要CountDownLatch也是同步的
            if (ifNeedWait(task)){
                mNeedWaitTasks.add(task);
                mNeedWaitCount.getAndIncrement();
            }
        }
        return this;
    }

    @UiThread
    public void start(){
        mStartTime = System.currentTimeMillis();
        if (Looper.getMainLooper() != Looper.myLooper()){
            throw new RuntimeException("must be called from UiThread");
        }

        if (mAllTasks.size() > 0){
            mAnaylyseCount.getAndIncrement();

            printDependedMsg();

            mAllTasks = TaskSort.sort(mAllTasks,mClsAllTasks);
            //需要被等待的数量
            mCountDownLatch = new CountDownLatch(mNeedWaitCount.get());
            //分发异步任务
            sendAndExecuteAsyncTasks();
            Logger.d(TAG,"task analyse cost " + (System.currentTimeMillis() - mStartTime) + "  begin main ");
            //分发主线程Task
            executeTaskMain();
        }
        Logger.d(TAG,"task analyse cost startTime cost " + (System.currentTimeMillis() - mStartTime));
    }

    /**
     * 取消
     */
    public void cancel(){
        for (Future future : mFutures){
            future.cancel(true);
        }
    }

    public void markTaskDone(Task task){
        if (ifNeedWait(task)){
            mFinishedTasks.add(task.getClass());
            mNeedWaitTasks.remove(task);
            mCountDownLatch.countDown();
            mNeedWaitCount.getAndDecrement();
        }
    }


    private void collectDepends(Task task){
        if (task.dependsOn() != null && task.dependsOn().size() > 0){
            for (Class<?extends Task> clazz : task.dependsOn()){
                if (mDependedHashMap.get(clazz) == null){
                    mDependedHashMap.put(clazz,new ArrayList<Task>());
                }
                mDependedHashMap.get(clazz).add(task);
                if (mFinishedTasks.contains(clazz)){
                    task.countDownForDepends();
                }
            }
        }
    }

    private boolean ifNeedWait(Task task){
        return !task.runOnMainThread() && task.needWait();
    }

    private void sendAndExecuteAsyncTasks(){
        for (Task task : mAllTasks){
            if (task.onlyInMainProcess() && !sIsMainProcess){
                //非主进程 直接结束Task
                markTaskDone(task);
            } else {
                sendTaskReal(task);
            }
            task.setState(State.DISPATCH);
        }
    }

    private void executeTaskMain(){
        mStartTime = System.currentTimeMillis();
        for (Task task:mMainThreadTasks){
            long time = System.currentTimeMillis();
            new DispatchRunnable(task,this).run();
            Logger.d(TAG,"real main " + task.getName() + " cost   " +
                    (System.currentTimeMillis() - time));
        }
        Logger.d(TAG,"maintask cost " + (System.currentTimeMillis() - mStartTime));
    }


    private void sendTaskReal(final Task task){
        if (task.runOnMainThread()){
            mMainThreadTasks.add(task);
            if (task.needCall()){
                task.setTaskCallBack(new ITaskCallBack() {
                    @Override
                    public void call() {
                        TaskStat.markTaskDone();
                        task.setState(State.FINISHED);
                        satisfyChildren(task);
                        Logger.d(TAG,task.getName() + " finish");
                    }
                });
            }
        } else {
            //直接分发，提交到线程池执行
            Future future = task.runOn().submit(new DispatchRunnable(task,this));
            mFutures.add(future);
        }
    }

    public void satisfyChildren(Task task){
        //获取所依赖的task
        List<Task> list = mDependedHashMap.get(task.getClass());
        if (list != null && list.size() > 0){
            for (Task t : list){
                t.countDownForDepends();
            }
        }
    }

    /**
     * 查看被依赖的信息
     */
    private void printDependedMsg() {
        Logger.d(TAG,"needWait size : " + (mNeedWaitCount.get()));
        if (false) {
            for (Class<? extends Task> cls : mDependedHashMap.keySet()) {
                Logger.d(TAG,"cls " + cls.getSimpleName() + "   " + mDependedHashMap.get(cls).size());
                for (Task task : mDependedHashMap.get(cls)) {
                    Logger.d(TAG,"cls       " + task.getName());
                }
            }
        }
    }
}
