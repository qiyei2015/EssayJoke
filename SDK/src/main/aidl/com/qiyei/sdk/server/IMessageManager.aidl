// IMessageManager.aidl
package com.qiyei.sdk.server;

// Declare any non-default types here with import statements
import com.qiyei.sdk.server.IMessageListener;

interface IMessageManager {

     /**
     * 注册listener
     *
     */
    void registerListener(in IMessageListener listener);
     /**
     * 取消listener注册
     *
     */
    void unregisterListener(in IMessageListener listener);

}
