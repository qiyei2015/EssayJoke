package com.qiyei.architecture.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qiyei.architecture.R;
import com.qiyei.architecture.common.AppdemoConstant;
import com.qiyei.architecture.ui.service.TestService;
import com.qiyei.sdk.dc.DataManager;
import com.qiyei.sdk.dc.DataObserver;
import com.qiyei.sdk.dc.impl.DC;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;

import java.util.HashSet;
import java.util.Set;

public class DataCenterTestActivity extends AppCompatActivity {

    private Button mButton;

    private static final String TAG = "Activity";

    Intent intent = null;
    ServiceConnection mServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_center_test);
        mButton = (Button) findViewById(R.id.btn1);

        final String uri1 = DataManager.getInstance().getUri(AppdemoConstant.StoreData.class, AppdemoConstant.StoreData.KEY_11);
        final String uri2 = DataManager.getInstance().getUri(AppdemoConstant.UserData.class,AppdemoConstant.UserData.KEY_12);
        final String uri3 = DataManager.getInstance().getUri(AppdemoConstant.MemoryData.class, AppdemoConstant.MemoryData.KEY_13);
        final String uri4 = DataManager.getInstance().getUri(AppdemoConstant.DefaultData.class,AppdemoConstant.DefaultData.KEY_14);
        final String uri5 = DataManager.getInstance().getUriForSecret(AppdemoConstant.DefaultData.class,AppdemoConstant.DefaultData.KEY_15);

        final Float d = 124.00000f;
        intent = new Intent(DataCenterTestActivity.this, TestService.class);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().setInt(uri1,1234567890);
                DataManager.getInstance().setFloat(uri2,d);
                DataManager.getInstance().setString(uri3,"90");
                DataManager.getInstance().setString(uri4,"100");
                DataManager.getInstance().setString(uri5,"10000");
//                Intent intent = new Intent(DataCenterTestActivity.this, ActivityDialog.class);
//                startActivity(intent);
                Intent intent = new Intent(DataCenterTestActivity.this, TestService.class);
                mServiceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        Set<String> uris = new HashSet<>();
        uris.add(uri1);
        uris.add(uri2);
        uris.add(uri3);
        uris.add(uri4);
        uris.add(uri5);

        DataManager.getInstance().registerDataObserver(uris,new DataObserver() {
            @Override
            public void onDataChanged(Set<String> uris) {
                LogManager.d(DC.TAG," uris ---->" + uris.toString());

                ToastUtil.showLongToast(DataManager.getInstance().getFloat(uri4,0) + "");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogManager.i(TAG,"onStart555");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogManager.i(TAG,"onResume5");
    }

    @Override
    protected void onPause() {
        super.onPause();

        LogManager.i(TAG,"onPause4444");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogManager.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        LogManager.i(TAG,"onDestroy");
        if (mServiceConnection != null){
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }


}
