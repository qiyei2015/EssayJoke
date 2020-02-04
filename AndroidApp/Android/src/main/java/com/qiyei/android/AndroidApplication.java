package com.qiyei.android;


import android.content.Context;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.util.Printer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;
import com.qiyei.framework.FrameworkApplication;
import com.qiyei.framework.database.room.AppData;
import com.qiyei.framework.database.room.AppDatabase;
import com.qiyei.framework.skin.SkinManager;
import com.qiyei.performance.bootstarter.TaskDispatcher;
import com.qiyei.performance.bootstarter.task.MainTask;
import com.qiyei.performance.bootstarter.task.Task;
import com.qiyei.performance.frame.FrameManager;
import com.qiyei.performance.hook.ARTHookManager;
import com.qiyei.sdk.SDKManager;
import com.qiyei.sdk.database.DatabaseManager;
import com.qiyei.sdk.log.LogManager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Created by qiyei2015 on 2017/5/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class AndroidApplication extends FrameworkApplication {

    private static final String TAG = "AndroidApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        TaskDispatcher.init(this);

        initARouter();
        initDataBase(this);

//        Task task1 = new Task.Builder()
//                .setName("initARouter")
//                .setTask(new Runnable() {
//                    @Override
//                    public void run() {
//                        initARouter();
//                    }
//                })
//                .build();

        Task task2 = new Task.Builder()
                .setName("initBlockCanary")
                .setTask(new Runnable() {
                    @Override
                    public void run() {
                        initBlockCanary();
                    }
                })
                .build();

        Task task3 = new Task.Builder()
                .setName("initSkinManager")
                .setTask(new Runnable() {
                    @Override
                    public void run() {
                        initSkinManager();
                    }
                })
                .build();

        Task task4 = new Task.Builder()
                .setName("initARTHook")
                .setTask(new Runnable() {
                    @Override
                    public void run() {
                        ARTHookManager.start();
                    }
                })
                .build();

        Task task5 = new Task.Builder()
                .setName("initFrameCallback")
                .setTask(new Runnable() {
                    @Override
                    public void run() {
                        initFrameCallback();
                    }
                })
                .build();

        Task task6 = new Task.Builder()
                .setName("initStrictMode")
                .setTask(new Runnable() {
                    @Override
                    public void run() {
                        initStrictMode();
                    }
                })
                .build();

        TaskDispatcher.getInstance()
                .addTask(task2)
                .addTask(task3)
                .addTask(task4)
                .addTask(task5)
                .addTask(task6)
                .start();

        Looper.getMainLooper().setMessageLogging(new Printer() {

            private static final String StartTAG = ">>>>>";
            private static final String EndTAG = "<<<<<";
            long time = 0;

            @Override
            public void println(String msg) {
                if (msg.contains(StartTAG)){
                    time = System.currentTimeMillis();
                } else if (msg.contains(EndTAG)){
                    long cost = System.currentTimeMillis() - time;
                    if (cost > 16){

                    }
                    Log.e("EEE","cost:" + cost);
                }
            }
        });
    }


    private void fff(){
        TaskDispatcher.getInstance().addTask(new MainTask() {
            @Override
            public String getName() {
                return "initARouter";
            }

            @Override
            public void run() {
                initARouter();
            }
        }).addTask(new Task() {
            @Override
            public String getName() {
                return "initBlockCanary";
            }

            @Override
            public void run() {
                initBlockCanary();
            }
        }).addTask(new Task() {
            @Override
            public String getName() {
                return "initSkinManager";
            }

            @Override
            public void run() {
                initSkinManager();
            }
        }).addTask(new MainTask() {
            @Override
            public String getName() {
                return "initBitmapHook";
            }

            @Override
            public void run() {
                ARTHookManager.start();
            }
        }).addTask(new MainTask() {
            @Override
            public String getName() {
                return "initFrameCallback";
            }

            @Override
            public void run() {
                initFrameCallback();
            }
        }).addTask(new Task() {
            @Override
            public String getName() {
                return "initStrictMode";
            }

            @Override
            public void run() {
                initStrictMode();
            }
        }).start();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private void initFrameCallback(){
        FrameManager.start();
    }

    private void initStrictMode(){
        //使用严格模式，检测内存泄漏
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .detectLeakedSqlLiteObjects()
                //类实例限制
                .setClassInstanceLimit(SDKManager.class,1)
                .detectLeakedClosableObjects()
                .penaltyLog()//违规对log日志
                .build());
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectResourceMismatches()
                .penaltyLog()
                .build());
    }

    private void initDataBase(Context context) {
        DatabaseManager.getInstance().init(this);
        DatabaseManager.getInstance().registerRoomDatabase(AppDatabase.class,
                AppData.DB_NAME,AppData.MIGRATION_1_2);
    }

    private void initBlockCanary() {
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    private void initSkinManager() {
        LogManager.i("DispatchRunnable","initSkinManager running");
        //初始化皮肤管理器
        SkinManager.getInstance().init(this);
    }

    private void initARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }

    class AppBlockCanaryContext extends BlockCanaryContext {

        /**
         * Implement in your project.
         *
         * @return Qualifier which can specify this installation, like version + flavor.
         */
        @Override
        public String provideQualifier() {
            return "1.0.0";
        }

        /**
         * Implement in your project.
         *
         * @return user id
         */
        @Override
        public String provideUid() {
            return "com.qiyei";
        }

        /**
         * Network type
         *
         * @return {@link String} like 2G, 3G, 4G, wifi, etc.
         */
        @Override
        public String provideNetworkType() {
            return "wifi";
        }

        /**
         * Config monitor duration, after this time BlockCanary will stop, use
         * with {@code BlockCanary}'s isMonitorDurationEnd
         *
         * @return monitor last duration (in hour)
         */
        @Override
        public int provideMonitorDuration() {
            return -1;
        }

        /**
         * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
         * from performance of device.
         *
         * @return threshold in mills
         */
        @Override
        public int provideBlockThreshold() {
            return 1000;
        }

        /**
         * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
         * stack according to current sample cycle.
         * <p>
         * Because the implementation mechanism of Looper, real dump interval would be longer than
         * the period specified here (especially when cpu is busier).
         * </p>
         *
         * @return dump interval (in millis)
         */
        @Override
        public int provideDumpInterval() {
            return provideBlockThreshold();
        }

        /**
         * Path to save log, like "/blockcanary/", will save to sdcard if can.
         *
         * @return path of log files
         */
        @Override
        public String providePath() {
            return "/blockcanary/";
        }

        /**
         * If need notification to notice block.
         *
         * @return true if need, else if not need.
         */
        @Override
        public boolean displayNotification() {
            return true;
        }

        /**
         * Implement in your project, bundle files into a zip file.
         *
         * @param src  files before compress
         * @param dest files compressed
         * @return true if compression is successful
         */
        @Override
        public boolean zip(File[] src, File dest) {
            return false;
        }

        /**
         * Implement in your project, bundled log files.
         *
         * @param zippedFile zipped file
         */
        @Override
        public void upload(File zippedFile) {
            throw new UnsupportedOperationException();
        }


        /**
         * Packages that developer concern, by default it uses process name,
         * put high priority one in pre-order.
         *
         * @return null if simply concern only package with process name.
         */
        @Override
        public List<String> concernPackages() {
            return null;
        }

        /**
         * Filter stack without any in concern package, used with @{code concernPackages}.
         *
         * @return true if filter, false it not.
         */
        @Override
        public boolean filterNonConcernStack() {
            return false;
        }

        /**
         * Provide white list, entry in white list will not be shown in ui list.
         *
         * @return return null if you don't need white-list filter.
         */
        @Override
        public List<String> provideWhiteList() {
            LinkedList<String> whiteList = new LinkedList<>();
            whiteList.add("org.chromium");
            return whiteList;
        }

        /**
         * Whether to delete files whose stack is in white list, used with white-list.
         *
         * @return true if delete, false it not.
         */
        @Override
        public boolean deleteFilesInWhiteList() {
            return true;
        }

        /**
         * Block interceptor, developer may provide their own actions.
         */
        @Override
        public void onBlock(Context context, BlockInfo blockInfo) {

        }
    }
}
