package com.qiyei.appdemo.binder;

import android.os.RemoteException;

import com.qiyei.appdemo.ICompute;


/**
 * Created by qiyei2015 on 2017/4/9.
 * 1273482124@qq.com
 */
public class ComputeImpl extends ICompute.Stub{


    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}

