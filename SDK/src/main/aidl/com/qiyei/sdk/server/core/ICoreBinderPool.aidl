// ICoreBinderPool.aidl
package com.qiyei.sdk.server.core;

// Declare any non-default types here with import statements

interface ICoreBinderPool {
    /**
    * 根据name查询对应的Binder
    **/
    IBinder queryBinder(in String name);

    /**
    * 根据code查询对应的Binder
    **/
    void addBinder(in String name,IBinder binder);

    /**
    * 根据code查询对应的Binder
    **/
    void removeBinder(in String name);

}
