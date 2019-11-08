package com.qiyei.architecture.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiyei.architecture.R;
import com.qiyei.architecture.service.IControlService;
import com.qiyei.architecture.service.impl.ControlImpl;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_test);
        dynamicProxy();
    }

    private void dynamicProxy(){
        final ControlImpl control = new ControlImpl();

        IControlService controlProxy = (IControlService) Proxy.newProxyInstance(control.getClass().getClassLoader(), control.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LogManager.d("invoke","first invoke method !");

                return method.invoke(control,args);
            }
        });
        controlProxy.printHello("hello world !");
    }

}
