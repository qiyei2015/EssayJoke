package com.qiyei.sdk.xml;

import com.qiyei.sdk.R;
import com.qiyei.sdk.common.RuntimeEnv;

import java.io.InputStream;

/**
 * @author Created by qiyei2015 on 2017/12/31.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class XmlManager {


    public static void readConfigure(){
        InputStream inputStream = RuntimeEnv.appContext.getResources().openRawResource(R.raw.configure);
        Dom4jHelper.parseXml(inputStream);
    }
}
