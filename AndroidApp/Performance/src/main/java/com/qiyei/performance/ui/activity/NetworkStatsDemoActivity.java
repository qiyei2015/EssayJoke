package com.qiyei.performance.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.os.Bundle;

import com.qiyei.performance.R;

/**
 * @author Created by qiyei2015 on 2019/11/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class NetworkStatsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_stats_demo);

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(Context.NETWORK_STATS_SERVICE);

        findViewById(R.id.button).setOnClickListener(view ->{
            //networkStatsManager.queryDetailsForUid()
        });

    }
}
