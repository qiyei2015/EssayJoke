package com.qiyei.architecture.ui.activity;

import android.content.Context;

import android.os.Bundle;

import com.qiyei.architecture.R;
import com.qiyei.sdk.log.LogManager;

import java.util.concurrent.TimeUnit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManagerDemoActivity extends AppCompatActivity {

    private static final String TAG = "WorkManagerDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager_demo);
        findViewById(R.id.btn).setOnClickListener( v -> {
            startWorkManager();
        });
    }

    private void startWorkManager(){

        //2 创建约束条件
        Constraints myConstraints = new Constraints.Builder()
                //设备idle是否运行
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                //设置出发的最大的延迟时间
                .build();

        //3 创建WorkRequest对象
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                .setConstraints(myConstraints)
                .setInitialDelay(2000, TimeUnit.MILLISECONDS)
                .build();
        //4 入队列
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

        //5 取消任务
//        UUID taskId = oneTimeWorkRequest.getId();
//        WorkManager.getInstance(this).cancelWorkById(taskId);
    }

    //1 继承worker
    private static class MyWork extends Worker{
        public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            LogManager.d(TAG,"MyWork start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LogManager.d(TAG,"MyWork end");
            return Result.success();
        }
    }
}
