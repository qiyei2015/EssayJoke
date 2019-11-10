package com.qiyei.opensource.ui.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.opensource.R;
import com.taobao.sophix.SophixManager;

public class HotfixTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotfix_test);

        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

}
