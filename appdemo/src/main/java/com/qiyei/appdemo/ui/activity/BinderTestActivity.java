package com.qiyei.appdemo.ui.activity;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.appdemo.R;
import com.qiyei.sdk.server.core.CoreBinderManager;
import com.qiyei.sdk.server.core.ICompute;
import com.qiyei.sdk.util.ToastUtil;

public class BinderTestActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_test);

        mButton = (Button) findViewById(R.id.btn1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBinder binder = CoreBinderManager.getInstance().queryBinder("compute");
                ICompute compute = ICompute.Stub.asInterface(binder);
                if (compute == null){
                    return;
                }
                try {
                    ToastUtil.showLongToast("调用 ICompute 的add : " + compute.add(20,30));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
