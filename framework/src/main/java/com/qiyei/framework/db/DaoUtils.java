package com.qiyei.framework.db;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/3.
 * Version: 1.0
 * Description: 数据库Dao工具类
 */
public class DaoUtils {

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

}
