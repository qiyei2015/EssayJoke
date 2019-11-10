package com.qiyei.sdk.database.engine.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.database.IQueryBuilder;
import com.qiyei.sdk.db.DaoUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/6/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class SQLiteQueryBuilder<T> implements IQueryBuilder{
    /**
     * 查询的列
     */
    private String[] mColumns;
    /**
     * 查询的条件
     */
    private String mSelection;
    /**
     * 查询的参数
     */
    private String[] mSelectionArgs;
    /**
     * 查询分组
     */
    private String mGroupBy;
    /**
     * 查询对结果集进行过滤
     */
    private String mHaving;
    /**
     * 查询排序
     */
    private String mOrderBy;
    /**
     * 查询可用于分页
     */
    private String mLimit;
    /**
     * 查询的class
     */
    private Class<T> mClass;
    /**
     * 数据库
     */
    private SQLiteDatabase mDatabase;

    public SQLiteQueryBuilder(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mClass = clazz;
        this.mDatabase = sqLiteDatabase;
    }

    /**
     * 设置查询的行数
     * @param columns
     * @return
     */
    @Override
    public SQLiteQueryBuilder columns(String... columns) {
        this.mColumns = columns;
        return this;
    }

    @Override
    public SQLiteQueryBuilder selectionArgs(String... selectionArgs) {
        this.mSelectionArgs = selectionArgs;
        return this;
    }

    @Override
    public SQLiteQueryBuilder having(String having) {
        this.mHaving = having;
        return this;
    }

    @Override
    public SQLiteQueryBuilder orderBy(String orderBy) {
        this.mOrderBy = orderBy;
        return this;
    }

    @Override
    public SQLiteQueryBuilder limit(String limit) {
        this.mLimit = limit;
        return this;
    }

    @Override
    public SQLiteQueryBuilder groupBy(String groupBy) {
        this.mGroupBy = groupBy;
        return this;
    }

    @Override
    public SQLiteQueryBuilder selection(String selection) {
        this.mSelection = selection;
        return this;
    }

    /**
     * 返回查询条件说查询到的
     * @return
     */
    @Override
    public List<T> query() {
        Cursor cursor = mDatabase.query(com.qiyei.sdk.db.DaoUtil.getTableName(mClass),mColumns,mSelection
                ,mSelectionArgs,mGroupBy,mHaving,mOrderBy);
        clearQueryParams();
        return cursorToList(cursor);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> queryAll() {
        Cursor cursor = mDatabase.query(com.qiyei.sdk.db.DaoUtil.getTableName(mClass),null,null,null,null,null,null);
        return cursorToList(cursor);
    }

    /**
     * 将数据库查询的cursor转换成List
     * @param cursor
     * @return
     */
    private List<T> cursorToList(Cursor cursor){
        List<T> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()){
            do {
                try {
                    T instance = mClass.newInstance();
                    Field[] fields = mClass.getDeclaredFields();

                    for (Field field : fields){
                        field.setAccessible(true);
                        //获取Filed的name
                        String name = field.getName();

                        int index = cursor.getColumnIndex(name);
                        if (index == -1){
                            continue;
                        }

                        //通过反射获取 游标的方法  field.getType() -> 获取的类型
                        Method cursorMethod = getCursorMethod(field.getType());
                        if (cursorMethod != null){
                            //通过反射获取了 value
                            Object value = cursorMethod.invoke(cursor,index);
                            if (value == null){
                                continue;
                            }

                            //处理特殊部分
                            // 处理一些特殊的部分
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }

                            field.set(instance,value);
                        }
                    }

                    //加入到集合中
                    list.add(instance);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * 根据clazz获取cursor中的方法
     * @param clazz
     * @return
     */
    private Method getCursorMethod(Class<?> clazz) throws NoSuchMethodException {
        String methodName = getColumnMethodName(clazz);
        // type String getString(index); int getInt; boolean getBoolean
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }

    /**
     * 清空参数
     */
    private void clearQueryParams() {
        mColumns = null;
        mSelection = null;
        mSelectionArgs = null;
        mGroupBy = null;
        mHaving = null;
        mOrderBy = null;
        mLimit = null;
    }

}
