package com.qiyei.sdk.server.core;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.log.LogManager;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/23.
 * Version: 1.0
 * Description:
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CoreWakeUpService extends JobService {

    private static final String TAG = CoreWakeUpService.class.getSimpleName();

    private final int jobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(jobWakeUpId,
                new ComponentName(this,CoreWakeUpService.class));
        builder.setPeriodic(1000);  //1秒

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // 开启定时任务，定时轮寻 ， 看MessageService有没有被杀死
        // 如果杀死了启动  轮寻onStartJob

        // 判断服务有没有在运行
        boolean alive = serviceAlive(CoreService.class.getName());
        if(!alive){
            startService(new Intent(this,CoreService.class));
            LogManager.i(TAG,"startService CoreService");
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = activityManager.getRunningServices(1000);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String name = myList.get(i).service.getClassName().toString();
            if (name.contains(CoreService.class.getCanonicalName())){
                LogManager.i(TAG, "RunningServiceInfo name:" + name);
                if (name.equals(serviceName)) {
                    isWork = true;
                    break;
                }
            }
        }
        LogManager.i(TAG,serviceName + " isAlive " + isWork);
        return isWork;
    }
}
