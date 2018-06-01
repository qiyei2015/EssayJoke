package com.qiyei.mvp;

import com.qiyei.mvp.base.IPresenter;
import com.qiyei.mvp.base.IView;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface ILoginPresenter<V extends IView> extends IPresenter<V> {

    void login(String name,String password);




}
