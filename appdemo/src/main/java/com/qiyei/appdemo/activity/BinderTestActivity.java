package com.qiyei.appdemo.activity;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qiyei.appdemo.ICompute;
import com.qiyei.appdemo.R;
import com.qiyei.appdemo.binder.ComputeImpl;
import com.qiyei.sdk.server.core.CoreBinderPool;
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
                IBinder binder = CoreBinderPool.getInstance().queryBinder("compute");
                ICompute compute = ICompute.Stub.asInterface(binder);

                try {
                    ToastUtil.showLongToast("调用 ICompute 的add : " + compute.add(20,30));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //这个一般由服务端添加
        CoreBinderPool.getInstance().addBinder("compute",new ComputeImpl());

    }

}
