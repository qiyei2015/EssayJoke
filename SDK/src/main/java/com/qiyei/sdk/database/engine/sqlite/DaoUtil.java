package com.qiyei.sdk.database.engine.sqlite;

import android.text.TextUtils;

import java.util.Locale;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/3.
 * Version: 1.0
 * Description: 数据库Dao工具类
 */
public class DaoUtil {

    /**
     * 获取类的表名
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz){
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * 获取type的列类型
     * @param type
     * @return
     */
    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text";
        } else if (type.contains("int")) {
            value = " integer";
        } else if (type.contains("boolean")) {
            value = " boolean";
        } else if (type.contains("float")) {
            value = " float";
        } else if (type.contains("double")) {
            value = " double";
        } else if (type.contains("char")) {
            value = " varchar";
        } else if (type.contains("long")) {
            value = " long";
        }
        return value;
    }

    /**
     * 分析其
     * @param string
     * @return
     */
    public static String capitalize(String string) {
        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }

}
