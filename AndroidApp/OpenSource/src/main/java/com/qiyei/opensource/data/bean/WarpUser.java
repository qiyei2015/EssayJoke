package com.qiyei.opensource.data.bean;

import javax.inject.Inject;

/**
 * @author Created by qiyei2015 on 2018/10/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class WarpUser {

    private User mUser;

//    @Inject
    public WarpUser(User user) {
        mUser = user;
    }

    public String getName(){
        return mUser.getName();
    }
}
