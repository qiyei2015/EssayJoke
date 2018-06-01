package com.qiyei.appdemo.service;

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

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/23.
 * Version: 1.0
 * Description:
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private final int jobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(jobWakeUpId,
                new ComponentName(this,JobWakeUpService.class));
        builder.setPeriodic(2000);  //2秒

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // 开启定时任务，定时轮寻 ， 看MessageService有没有被杀死
        // 如果杀死了启动  轮寻onStartJob

        // 判断服务有没有在运行
        boolean messageServiceAlive = serviceAlive(LocalService.class.getName());
        if(!messageServiceAlive){
            startService(new Intent(this,LocalService.class));
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
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
