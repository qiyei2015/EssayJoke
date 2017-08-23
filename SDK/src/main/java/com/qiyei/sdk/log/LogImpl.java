package com.qiyei.sdk.log;

import java.io.PrintWriter;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/23.
 * Version: 1.0
 * Description:
 */
public class LogImpl {

    private String mFileName;

    private String mPath;

    /**
     * 打印流到文件
     */
    private PrintWriter mPrintWriter;


    public LogImpl(String fileName){
        mFileName = fileName;
    }


    private void print(String msg){
        mPrintWriter.println(msg);

    }


}
