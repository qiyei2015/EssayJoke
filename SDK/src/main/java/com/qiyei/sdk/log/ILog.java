package com.qiyei.sdk.log;

/**
 * @author Created by qiyei2015 on 2020/1/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface ILog {

    /**
     * 日志打印接口
     * @param level
     * @param tag
     * @param msg
     */
    void print(int level,String tag, String msg);

    /**
     * 设置是否写入文件
     * @param isWrite
     */
    void setWriteFile(boolean isWrite);
}
