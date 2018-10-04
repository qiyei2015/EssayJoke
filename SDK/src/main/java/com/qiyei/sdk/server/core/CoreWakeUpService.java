package com.qiyei.sdk.server.core;

import android.annotation.TargetApi;

import android.app.Notification;
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



import static com.qiyei.sdk.common.RuntimeEnv.serviceAlive;

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
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            LogManager.i(TAG,"startForeground");
            startForeground(1,new Notification());
        }
    }

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
        boolean alive = RuntimeEnv.serviceAlive(CoreService.class.getName());
        if(!alive){
            //startService Android 8.0以上不支持启动在后台的service
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
                startForegroundService(new Intent(this,CoreService.class));
            }else {
                startService(new Intent(this,CoreService.class));
            }
            LogManager.i(TAG,"startService CoreService");
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
