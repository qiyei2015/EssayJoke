package com.qiyei.android;


import android.content.Context;
import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;
import com.qiyei.framework.FrameworkApplication;
import com.qiyei.framework.skin.SkinManager;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class BaseApplication extends FrameworkApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

//        try {
//            SDKManager.initSDK(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //初始化皮肤管理器
        SkinManager.getInstance().init(this);
        //使用严格模式，检测内存泄漏
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()//监测所以内容
                .penaltyLog()//违规对log日志
                .penaltyDeath()
                .build());


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
