package com.qiyei.sdk.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by qiyei2015 on 2019/4/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ExecutorManager {
    private static final String TAG = "ExecutorManager";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * We want at least 2 threads and at most 4 threads in the core pool,
     * preferring to have 1 less than the CPU count to avoid saturating
     * he CPU with background work
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final int QUEUE_SIZE = 128;
    private static final int IO_MAX = 128;

    /**
     * CPU密集型线程池
     */
    private static final ThreadPoolExecutor sCPUThreadPoolExecutor;
    /**
     * IO密集型线程池
     */
    private static final ThreadPoolExecutor sIOThreadPoolExecutor;

    private static final ThreadFactory sThreadFactory = new DefaultThreadFactory();

    /**
     * The default thread factory.
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = TAG + " - " + poolNumber.getAndIncrement() + "-Thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()){
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY){
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(QUEUE_SIZE);

    /**
     * 一般不会到这里
     */
    private static final RejectedExecutionHandler sHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Executors.newCachedThreadPool().execute(r);
        }
    };

    static {
        ThreadPoolExecutor cpuThreadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        cpuThreadPool.allowCoreThreadTimeOut(true);
        sCPUThreadPoolExecutor = cpuThreadPool;
        ThreadPoolExecutor ioThreadPool = new ThreadPoolExecutor(
                0, IO_MAX, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        ioThreadPool.allowCoreThreadTimeOut(true);
        sIOThreadPoolExecutor = ioThreadPool;
    }

    /**
     * 获取CPU线程池
     * @return
     */
    public static ThreadPoolExecutor getCPUExecutor() {
        return sCPUThreadPoolExecutor;
    }

    /**
     * 获取IO线程池
     * @return
     */
    public static ExecutorService getIOExecutor() {
        return sIOThreadPoolExecutor;
    }


}
