package com.qiyei.sdk.server.binder;

import android.os.RemoteException;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.core.CoreBinderConstant;
import com.qiyei.sdk.server.core.ICompute;


/**
 * Created by qiyei2015 on 2017/4/9.
 * 1273482124@qq.com
 */
public class ComputeImpl extends ICompute.Stub{

    @Override
    public int add(int a, int b) throws RemoteException {
        LogManager.i(CoreBinderConstant.TAG,"ComputeImpl add : " + a + " + " + b);
        return a + b + 1;
    }

}

