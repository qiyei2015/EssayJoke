package com.qiyei.sdk.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/25.
 * Version: 1.0
 * Description: 作用于方法上，运行时注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSuccess {
    /**
     * 权限申请失败
     * @return
     */
    int requestCode();

}
