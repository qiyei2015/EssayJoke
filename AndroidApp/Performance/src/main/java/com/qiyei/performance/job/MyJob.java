package com.qiyei.performance.job;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * @author Created by qiyei2015 on 2019/11/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MyJob extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
