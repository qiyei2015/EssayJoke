package com.qiyei.sdk.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/2.
 * Version: 1.0
 * Description: View注解的Annotation
 */

/**
 * @Target(ElementType.FIELD) 代表Annotation的位置  FIELD属性  TYPE类上  CONSTRUCTOR 构造函数上
 * @Retention(RetentionPolicy.CLASS) 什么时候生效 CLASS编译时   RUNTIME运行时  SOURCE源码资源
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    /**
     * --> @ViewById(R.id.xxx)
     * @return
     */
    int value();
}
