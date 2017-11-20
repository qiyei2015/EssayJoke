package com.qiyei.sdk.database;

import com.qiyei.sdk.util.AndroidUtil;

import java.io.File;

/**
 * @author Created by qiyei2015 on 2017/11/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DB {

    /**
     * 调试标志
     */
    public static final String TAG = "<DB>";

    /**
     * 数据库路径，存放在Android/data/xxx.xxx.xxx/database/下
     */
    public static final String DATABASE_PATH = AndroidUtil.getExternalDataPath() + File.separator
            + "database" + File.separator;


}
